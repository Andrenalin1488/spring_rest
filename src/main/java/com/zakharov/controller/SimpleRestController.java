package com.zakharov.controller;

import com.zakharov.service.FileService;
import lombok.SneakyThrows;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/get")
public class SimpleRestController {

    private static final String FILE_EXTENSION = ".xlsx";
    private static final String CONTENT_DISPOSITION = "attachment; filename=";
    public static final String APPLICATION_VND_MS_EXCEL = "application/vnd.ms-excel";
    public static final String FORMAT = "%s%s%s";
    private final FileService fileService;

    public SimpleRestController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public String hello(@RequestParam String name) {
        return "Hello " + name + " from simple controller";
    }

    @GetMapping("/{name}")
    public String helloPath(@PathVariable String name) {
        return "Hello " + name + " from simple controller";
    }

    @PostMapping("/sendXls")
    public String sendXls(@RequestParam MultipartFile file) {
        fileService.uploadFile(file);
        return "File send successfully";
    }

    @SneakyThrows
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        InputStreamResource file = new InputStreamResource(fileService.download());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format(FORMAT, CONTENT_DISPOSITION, filename, FILE_EXTENSION))
                .contentType(MediaType.parseMediaType(APPLICATION_VND_MS_EXCEL))
                .body(file);
    }
}


