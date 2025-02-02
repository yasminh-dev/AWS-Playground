package com.learning.pollydemo;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.VoiceId;

import java.util.Scanner;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;


public class PollyDemo {
    AmazonPolly client = AmazonPollyClientBuilder.defaultClient();

    public void synthesizeSpeech(String speechText) throws JavaLayerException {
        // String outputFileName = "speech.mp3";

        SynthesizeSpeechRequest synthesizeSpeechRequest = new SynthesizeSpeechRequest()
                .withOutputFormat(OutputFormat.Mp3)
                .withVoiceId(VoiceId.Brian)
                .withText(speechText);

        SynthesizeSpeechResult synthesizeSpeechResult = client.synthesizeSpeech(synthesizeSpeechRequest);

        //create an MP3 player
        AdvancedPlayer player = new AdvancedPlayer(synthesizeSpeechResult.getAudioStream(),
                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

        player.setPlayBackListener(new PlaybackListener() {
            @Override
            public void playbackStarted(PlaybackEvent evt) {
                System.out.println("Playback started");
                System.out.println(speechText);
            }

            @Override
            public void playbackFinished(PlaybackEvent evt) {
                System.out.println("Playback finished");
            }
        });

        player.play();
    }

    public static void main(String[] args) throws JavaLayerException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the speech you would like Amazon Polly to say:");
        String input = scanner.nextLine();
        scanner.close();
        PollyDemo sample = new PollyDemo();

        sample.synthesizeSpeech(input);
    }
}