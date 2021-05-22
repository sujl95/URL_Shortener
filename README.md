<div align="center">
<h1>Url-Shortener</h1>
</div>

## 1.Subject

URL을 입력받아 짧게 줄여주고, Shortening된 URL을 입력하면 원래 URL로 리다이렉트하는 URL Shortening Service

    - URL 입력폼 제공 및 결과 출력
    - URL Shortening Key는 8 Character 이내로 생성되어야 합니다.
    - 동일한 URL에 대한 요청은 동일한 Shortening Key로 응답해야 합니다.
    - 동일한 URL에 대한 요청 수 정보를 가져야 한다(화면 제공 필수 아님)
    - Shortening된 URL을 요청받으면 원래 URL로 리다이렉트 합니다.
    - Database 사용은 필수 아님
    - 예 ( http://localhost:8080/UnFYVWl1 )
        - 도메인 사용시 
            - thewing.kr/UnFYVWl1

### 1.1 기획

- Spring Boot , Java를 활용하여 개발
- 동시성 이슈를 해결하기 위해 HashMap 대신 ConcurrentHashMap 사용
- 단축 URL 알고리즘을 AutoIncrement ID에 따라 BASE62를 사용하여 진행하려 했으나 아래 예시와 같이 
  8자리가 아닌 규칙적으로 URL이 생성 되었었다.
- 예시)
  - 첫 번째 ShortURL 저장 결과 
       - http://thewing.kr/A
  - 두 번째 ShortURL 저장 결과
    - http://thewing.kr/B
- 이렇게 나오기 때문에 8자리 맞추기 위하여 원본 URL을 SHA256으로 해시화 후, Base64로 인코딩 한 뒤 64자리중 앞 8자리를 잘랐다.
  충돌할 시에는 한 자리씩 밀어서 잘랐다. (0, 7),(1, 8),(2, 9)~~~
  

## 2.Environment

- Java 11
- Gradle
- Spring boot

## 3.Install

- Repository Clone

    - `$ git clone https://github.com/sujl95/URL_Shortener.git`

## 4.Use    

### 사용법

### 예시 
1. Run  
2. Post http://localhost:8080 

json
```json
{
    "url" : "https://www.naver.com"
}
```
    

3. Get http://localhost:8080/{ShortUrl}
```
예시
http://localhost:8080/UnFYVWl1
```

    

4. Url 정보 http://localhost:8080/logs/{ShortUrl}


예시    
http://localhost:8080/logs/UnFYVWl1
응답 예시
```json
{
  "shortUrl": "UnFYVWl1",
  "originUrl": "https://www.naver.com",
  "requestCount": 0,
  "ip": [
    {
      "ip": "0:0:0:0:0:0:0:1",
      "accessDate": "2021-04-04T01:20:05.7995907"
    }
  ]
}
```
requestCount = 요청 횟수
ip : 요청 IP
accessDate : 접근 시간

