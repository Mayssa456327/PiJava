package com.example.pidevjava.controllers;

import com.example.pidevjava.services.ServiceEvenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class EvenementController implements Initializable {

    @FXML
    private Label DocDirect;

    @FXML
    private Button Evenementbtn;

    @FXML
    private Button Sponsorbtn1;

    @FXML
    private Button BackEventbtn;

    private Voice voice;
    private final ServiceEvenement SE = new ServiceEvenement();

    @FXML
    void EvenementAction(ActionEvent event) {
        SE.changeScreen(event,"/com/example/pidevjava/FrontEvenement.fxml", "afficher front Evenement");
        speak("Event");
    }

    @FXML
    void SponsorAction(ActionEvent event) {
        SE.changeScreen(event,"/com/example/pidevjava/BackSponsor.fxml", "afficher Back sponsor");
        speak("sponsors");
    }

    @FXML
    void BackAction(ActionEvent event) {
        SE.changeScreen(event,"/com/example/pidevjava/BackEvenement.fxml", "afficher Back evenement");
        speak("Back");
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the system property for FreeTTS voices directory
        /*System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        // Get the default voice
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice[] voices = voiceManager.getVoices();

        // Print available voices
        System.out.println("Available voices:");
        for (Voice v : voices) {
            System.out.println("- " + v.getName());
        }

        // Choose a voice (e.g., "kevin" or "kevin16")
        voice = voiceManager.getVoice("kevin");
        if (voice != null) {
            voice.allocate();
        } else {
            System.err.println("Cannot find voice: kevin16");
        }

        // Speak a welcome phrase
        speak("Bienvenue sur DocDirect");*/
    }

    private void speak(String phrase) {
        if (voice != null) {
            voice.speak(phrase);
        }
    }
}

// Autres méthodes et membres de la classe

