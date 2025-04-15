package backend.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.SynthesizerProperties;
import java.util.Locale;
import org.springframework.stereotype.Service;

@Service
public class TextToSpeechService {
    private static final String VOICES_KEY = "freetts.voices";
    private static final String VOICE_VALUE = "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory";
    private static final String CENTRAL_DATA = "com.sun.speech.freetts.jsapi.FreeTTSEngineCentral";
    private Synthesizer synthesizer;

    @PostConstruct
    public void init() {
        try {
            System.setProperty(VOICES_KEY, VOICE_VALUE);
            Central.registerEngineCentral(CENTRAL_DATA);
            synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
            synthesizer.resume();

            // Set the speech rate to a slower value
            SynthesizerProperties props = synthesizer.getSynthesizerProperties();
            props.setSpeakingRate(130.0f); // Default rate is usually around 150-180 words per minute

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void speak(String text) {
        try {
            if (synthesizer != null) {
                synthesizer.speakPlainText(text, null);
                synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void cleanUp() {
        try {
            if (synthesizer != null) {
                synthesizer.deallocate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
