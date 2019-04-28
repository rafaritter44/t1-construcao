package br.pucrs.construcao.t1.backend.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.codehaus.jackson.JsonProcessingException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.xml.XmlMapper;

import br.pucrs.construcao.t1.backend.exception.FileAccessException;
import br.pucrs.construcao.t1.backend.exception.XmlConversionException;

@Service
public class FileService {

	private final XmlMapper xmlMapper;
	
	public FileService() {
		this.xmlMapper = new XmlMapper();
	}
	
	public boolean fileExists(String path) {
		return Files.exists(Paths.get(path));
	}
	
	public void createDirectory(String path) {
		new File(path).mkdir();
	}
	
	public void createXmlFile(String fileName, Object content) throws FileAccessException, XmlConversionException {
		try {
			xmlMapper.writeValue(new File(fileName), content);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new XmlConversionException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileAccessException(e.getMessage());
		}
	}
	
	public <T> T readXmlFile(String fileName, Class<T> classOfT) throws FileAccessException, XmlConversionException {
		try {
			return xmlMapper.readValue(new File(fileName), classOfT);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new XmlConversionException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileAccessException(e.getMessage());
		}
	}
	
}
