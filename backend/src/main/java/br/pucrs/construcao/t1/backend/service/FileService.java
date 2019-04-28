package br.pucrs.construcao.t1.backend.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import br.pucrs.construcao.t1.backend.exception.FileAccessException;

@Service
public class FileService {
	
	public boolean fileExists(String path) {
		return Files.exists(Paths.get(path));
	}
	
	public void createDirectory(String path) {
		new File(path).mkdir();
	}
	
	public void createFile(String fileName, String content) throws FileAccessException {
		try (PrintWriter writer = new PrintWriter(fileName)) {
			writer.print(content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FileAccessException(e.getMessage());
		}
	}
	
}
