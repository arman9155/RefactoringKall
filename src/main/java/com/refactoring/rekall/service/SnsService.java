package com.refactoring.rekall.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.refactoring.rekall.Auth;
import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.entity.UserEntity;
import com.refactoring.rekall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SnsService {

    @Autowired
    UserRepository userRepository;

    public String getAccessToken (String auth_code) { // 토큰 가져오기
        String access_token = "";
        String refresh_token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=cb0f1cc9efcfef4061a974fee5bd040c");
            sb.append("&redirect_uri=http://localhost:8080/oauth/kakao");
            sb.append("&code=" + auth_code);
            bw.write(sb.toString());
            bw.flush();

            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_token);
            System.out.println("refresh_token : " + refresh_token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_token;
    }

    public HashMap<String, Object> getUserInfo (String access_token) {
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_token);
            int responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            String id = element.getAsJsonObject().get("id").getAsString();
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().getAsJsonObject().get("email").getAsString();

            if("".equals(email)){
                email = "null@example.com";
            }

            System.out.println("element" + element+"email"+email);

            userInfo.put("id", id);
            userInfo.put("name", nickname);
            userInfo.put("email", email);
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    public Object findUser(Object id, Object name) {
        List<UserEntity> userEntityList = userRepository.findAllByRole("kakao");
        if(userEntityList != null) {
            for(UserEntity user : userEntityList) {
                if(user.getUserId().equals(id)&&user.getStatus().equals("활동계정")){
                    return "T";
                }
            }
        }
        return "F";
    }

    public String saveSnsUSer(String id, String name, String email, Auth.Role status) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(id);
        userDTO.setName(name);
        userDTO.setPassword((id+status+"*"));
        userDTO.setPhoneNb("0100000000");
        userDTO.setEmail(email);
        userDTO.setRole(status);

       userRepository.save(UserEntity.toUserEntity(userDTO));

       return userDTO.getPassword();
    }

    public void deleteKakao(String access_Token) {
        String reqURL = "https://kapi.kakao.com/v1/user/unlink";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
