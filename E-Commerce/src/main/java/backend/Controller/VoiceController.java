package backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import backend.service.TextToSpeechService;

import java.util.Locale;  
import javax.speech.Central;  
import javax.speech.synthesis.Synthesizer;  
import javax.speech.synthesis.SynthesizerModeDesc;  

@Controller
public class VoiceController {
    
    @Autowired
    private TextToSpeechService textToSpeechService;
    
    @PostMapping("/processSpeech")
    @ResponseBody
    public String processSpeech(@RequestBody Transcript transcript) {
        // Process the transcript and generate a response
        String response = transcript.getTranscript();
        // You can add more complex logic here to generate a meaningful response
        return response;
    }

    public static class Transcript {
        private String transcript;

        public String getTranscript() {
            return transcript;
        }

        public void setTranscript(String transcript) {
            this.transcript = transcript;
        }
    }
}
