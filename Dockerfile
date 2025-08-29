FROM maven:3.9.6-eclipse-temurin-17

# Instala Chromium y dependencias
RUN apt-get update && \
    apt-get install -y \
      wget unzip curl \
      chromium-browser chromium-chromedriver \
      fonts-liberation libappindicator3-1 libasound2 libatk-bridge2.0-0 libatk1.0-0 libcups2 libdbus-1-3 libgdk-pixbuf2.0-0 libnspr4 libnss3 libx11-xcb1 libxcomposite1 libxdamage1 libxrandr2 libgbm1 libgtk-3-0 libxshmfence1 libxcb1 && \
    chmod +x /usr/bin/chromedriver

# Variables de entorno para Selenium
ENV CHROME_BIN=/usr/bin/chromium-browser
ENV CHROMEDRIVER=/usr/bin/chromedriver

# Copiar y cachear dependencias primero
COPY pom.xml /usr/src/app/
WORKDIR /usr/src/app
RUN mvn dependency:resolve

# Luego copia el resto del código
COPY . /usr/src/app

# Validaciones
RUN which chromium-browser || which chromium
RUN which chromedriver

# Entrada por default (puede ser sobreescrita en Jenkins)
CMD ["mvn", "clean", "test"]

