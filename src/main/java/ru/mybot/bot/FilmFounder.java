package ru.mybot.bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class FilmFounder {
    public static String getParser(String filmName, String dr_path) throws InterruptedException, IOException {
        String result = null;
        System.setProperty("webdriver.gecko.driver", dr_path);
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless"); // Установите headless-режим
        WebDriver webDriver = new FirefoxDriver(options);
        try {
            webDriver.get("https://torrent.aqproject.ru/account/login");
            //webDriver.manage().window().maximize();
            webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebElement webElementLogin = webDriver.findElement(By.xpath("//*[@id='LoginForm_username']"));
            WebElement webElementPassword = webDriver.findElement(By.xpath("//*[@id='LoginForm_password']"));
            WebElement webElementButt = webDriver.findElement(By.xpath("//*[contains(@type, 'submit')]"));
            webElementLogin.sendKeys("your_login_in_site");
            webElementPassword.sendKeys("your_password_in_site");
            webElementButt.click();
            if (isUserLoggedInSuccessfully(webDriver)) {
                WebElement webElement2 = webDriver.findElement(By.xpath("//*[text()='Торренты']"));
                String url = webElement2.getAttribute("href") + "?offset=";
                result = getFilm(filmName, dr_path, webDriver, url);
            }

            Thread.sleep(1000);

            WebElement webElementLogout = webDriver.findElement(By.xpath("//*[text()='Выход']"));
            webElementLogout.click();
            // Обработка диалогового окна подтверждения
            Alert alert = webDriver.switchTo().alert();
            alert.accept(); // Можно использовать dismiss(), чтобы отменить диалоговое окно

            Thread.sleep(1000);
            webDriver.quit();

        }catch (Exception ex) {}
        finally {
            webDriver.quit();
        }
        return result;
    }
    private static boolean isUserLoggedInSuccessfully(WebDriver webDriver) {
        // Ваш код для проверки успешной авторизации
        // Вернуть true, если авторизация успешна, иначе false
        // Например, можно проверить наличие элемента, который присутствует только после успешной авторизации
        try {
            webDriver.findElement(By.xpath("//*[text()='Выход']"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    private static String getFilm(String passWord, String dr_path, WebDriver webDriver, String url) throws IOException, InterruptedException {
        int str = 0;
        int number = 0;
        String result = null;
        String request = null;
/*        System.setProperty("webdriver.chrome.driver", dr_path);
        WebDriver webDriver = new ChromeDriver();*/
/*           System.setProperty("webdriver.edge.driver", dr_path);
            EdgeOptions options = new EdgeOptions();
            //options.addArguments("--remote-allow-origins=*");
            WebDriver webDriver = new EdgeDriver(options);*/
        System.setProperty("webdriver.gecko.driver", dr_path);
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless"); // Установите headless-режим
        //options.addArguments("--remote-allow-origins=*"); // Установите опцию --remote-allow-origins
        try {
            for (int i = 0; i < 80; i++) {
                webDriver.get(url + number);
                Document document = Jsoup.parse(webDriver.getPageSource());
                Elements elements = document.getElementsByClass("torrent-browse-name");

                for (Element element : elements) {
                    int idWord = element.text().indexOf("Прикреплен:");
                    if (element.text().replace("«", "").replace("»", "").replace("ё", "е").replace("Ё", "Е")
                            .replace(": ", " ").replace("-", " ").replace(", ", " ").replace("!", "").replace(". ", " ").toLowerCase().contains(passWord.toLowerCase())
                            || element.text().replace("«", "").replace("»", "").replace("ё", "е").replace("Ё", "Е")
                            .replace(": ", " ").replace("-", " ").replace(", ", " ").replace("!", "")
                            .replace(". ", " ").substring(idWord + 12).toLowerCase().contains(passWord.toLowerCase())) {

                        String webElementUrl = "https://torrent.aqproject.ru" + element.attr("href");
                        webDriver.get(webElementUrl);
                        Document documentUrl = Jsoup.parse(webDriver.getPageSource());
                        Elements elementsDown = documentUrl.getElementsByClass("btn btn-sm btn-aq-outline btn-aq-download");

                        result = element.text() + "\n" + "https://torrent.aqproject.ru" + element.attr("href") +
                                "\n" + "Ссылка для скачивания торрент файла: \n" +
                                "https://torrent.aqproject.ru" + elementsDown.attr("href");
                        str = i;
                        request = "Страница: " + str + "\n" + result;
                    }
                }
                if (result != null) {
                    break;
                } else {
                    number += 20;
                }
            }
        } catch (Exception e) {
            // Обработка ошибок, например, вывод сообщения или логгирование
            //e.printStackTrace();
        } /*finally {
            webDriver.quit();
        }*/

        return request;
    }
}
