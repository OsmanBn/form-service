package org.SpeechForm.FormService;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
@CrossOrigin(origins = "*")
@SpringBootApplication
@EnableConfigurationProperties(
		{FileStorageProperties.class}
)
public class FormServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormServiceApplication.class, args);
		/*Configuration config = new Configuration();
		config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		config.setDictionaryPath("src\\main\\resources\\6924.dic");
		config.setLanguageModelPath("src\\main\\resources\\6924.lm");

		try{
			LiveSpeechRecognizer speech = new LiveSpeechRecognizer(config);
			speech.startRecognition(true);

			SpeechResult speechResult = null;

			while((speechResult = speech.getResult()) != null){
				String voiceCommand = speechResult.getHypothesis();
				System.out.println("Voice Command is " + voiceCommand);

				if (voiceCommand.equalsIgnoreCase("Open Chrome")) {
					Runtime.getRuntime().exec("cmd.exe /c start chrome www.youtube.com");
				} else if (voiceCommand.equalsIgnoreCase("Close Chrome")){
					Runtime.getRuntime().exec("cmd.exe /c TASKKILL /IM chrome.exe");
				}
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}*/
	}

}
