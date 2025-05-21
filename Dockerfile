FROM maven:3.9.6-eclipse-temurin-17

RUN apt-get update && \
    apt-get install -y \
      wget unzip curl \
      chromium-browser chromium-chromedriver \
      fonts-liberation libappindicator3-1 libasound2 libatk-bridge2.0-0 libatk1.0-0 libcups2 libdbus-1-3 libgdk-pixbuf2.0-0 libnspr4 libnss3 libx11-xcb1 libxcomposite1 libxdamage1 libxrandr2 libgbm1 libgtk-3-0 libxshmfence1 libxcb1 && \
    chmod +x /usr/bin/chromedriver

ENV CHROME_BIN=/usr/bin/chromium-browser
ENV CHROMEDRIVER=/usr/bin/chromedriver

COPY . /usr/src/app
WORKDIR /usr/src/app

RUN which chromium-browser || which chromium
RUN which chromedriver

CMD ["mvn", "clean", "test"]
