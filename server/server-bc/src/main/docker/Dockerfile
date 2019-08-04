FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD server-wx/target/server-wx-1.0.0.RELEASE.jar server-wx.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/server-wx.jar"]
RUN echo 'Asia/Shanghai' >/etc/timezone
RUN apk --update add curl bash ttf-dejavu