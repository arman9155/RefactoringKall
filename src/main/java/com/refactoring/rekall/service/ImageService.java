package com.refactoring.rekall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    // /src/main/resources/static/upImg/"+categoryId(카테고리)+"/"+Id(3번째)+"/+UUID_name(2번째)_oriImgName"
    public String saveImg (String categoryId, String name, String Id, MultipartFile imgFile) throws Exception {

        if(imgFile != null && !imgFile.isEmpty()) {
            String oriImgName = imgFile.getOriginalFilename(); // 원본파일 이름

            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/upImg/"+categoryId+"/"+Id+"/"; //경로
            // UUID 를 이용하여 파일명 새로 생성
            // UUID - 서로 다른 객체들을 구별하기 위한 클래스

            UUID uuid = UUID.randomUUID(); // 난수 생성
            String savedFileName = uuid + "_" + name + "_" + oriImgName;  //저장할 이름 완성

            File saveFile = new File(projectPath, savedFileName);
            if(!saveFile.exists())
                saveFile.mkdirs();

            imgFile.transferTo(saveFile);

            String saveName = "/upImg/"+categoryId+"/"+Id+"/"+savedFileName;
            return saveName;
        }
        return "";
    }
}
