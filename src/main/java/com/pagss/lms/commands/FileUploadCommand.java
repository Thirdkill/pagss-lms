package com.pagss.lms.commands;

import lombok.Getter;
import lombok.Setter;

public class FileUploadCommand {

	@Getter @Setter private String fileName;
	@Getter @Setter private String fileDir;
	@Getter @Setter private String contentType;
}
