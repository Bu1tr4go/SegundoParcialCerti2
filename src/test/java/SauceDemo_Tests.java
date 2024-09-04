import com.google.common.collect.Ordering;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SauceDemo_Tests extends Base_SauceDemo{

    @Test
    public void Logout() throws InterruptedException {
        WebElement botonMenu = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("react-burger-menu-btn")));
        botonMenu.click();

        WebElement botonCerrarSesion = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("logout_sidebar_link")));
        Thread.sleep(800);
        botonCerrarSesion.click();

        WebElement botonLogin = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("login-button")));

        Assertions.assertTrue(botonLogin.isDisplayed());
    }

    @Test
    public void CompraCompleta() {
        WebElement botonAgregarBici = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("add-to-cart-sauce-labs-bike-light")));
        botonAgregarBici.click();

        WebElement botonAgregarMochila = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        botonAgregarMochila.click();

        WebElement botonAgregarChaqueta = driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket"));
        botonAgregarChaqueta.click();

        WebElement enlaceCarrito = driver.findElement(By.className("shopping_cart_link"));
        enlaceCarrito.click();

        WebElement botonCheckout = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("checkout")));
        botonCheckout.click();

        WebElement campoNombre = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("first-name")));
        campoNombre.sendKeys("Daniel");

        WebElement campoApellido = driver.findElement(By.id("last-name"));
        campoApellido.sendKeys("Torrico");

        WebElement campoCodigoPostal = driver.findElement(By.id("postal-code"));
        campoCodigoPostal.sendKeys("0000");

        WebElement botonContinuar = driver.findElement(By.id("continue"));
        botonContinuar.click();

        WebElement botonFinalizar = driver.findElement(By.id("finish"));
        botonFinalizar.click();

        WebElement imagenCompraExitosa = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("pony_express")));
        Assertions.assertTrue(imagenCompraExitosa.isDisplayed());
    }

    //BUG, La suma que muestra es de 105.96000000001
    @Test
    public void SumaPrecioProductos() throws InterruptedException {
        WebElement botonAgregarMochila = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("add-to-cart-sauce-labs-backpack")));
        botonAgregarMochila.click();

        WebElement botonAgregarCamisa = driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt"));
        botonAgregarCamisa.click();

        WebElement botonAgregarBici = driver.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        botonAgregarBici.click();

        WebElement botonAgregarChaqueta = driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket"));
        botonAgregarChaqueta.click();

        WebElement enlaceCarrito = driver.findElement(By.className("shopping_cart_link"));
        enlaceCarrito.click();

        WebElement botonCheckout = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("checkout")));
        botonCheckout.click();

        WebElement campoNombre = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("first-name")));
        campoNombre.sendKeys("Daniel");

        WebElement campoApellido = driver.findElement(By.id("last-name"));
        campoApellido.sendKeys("Torrico");

        WebElement campoCodigoPostal = driver.findElement(By.id("postal-code"));
        campoCodigoPostal.sendKeys("0000");

        WebElement botonContinuar = driver.findElement(By.id("continue"));
        botonContinuar.click();

        WebElement textoSubtotal = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("summary_subtotal_label")));
        String subtotal = textoSubtotal.getText();

        //La suma que muestra es de 105.96000000001
        Assertions.assertEquals(subtotal, "Item total: $105.96");
    }

    //BUG, se espera que el boton de checkout este desabilitado cuando el carrito este vacio
    @Test
    public void CompraConCarritoVacio() throws InterruptedException {
        WebElement carrito = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("shopping_cart_link")));
        carrito.click();

        WebElement botonCheckout = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("checkout")));
        Assertions.assertFalse(botonCheckout.isEnabled());
    }

    @Test
    public void RedireccionTwitter() throws InterruptedException {
        WebElement icono = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.linkText("Twitter")));
        String urlEsperada = "https://twitter.com/saucelabs";
        String urlActual = icono.getAttribute("href");
        Assertions.assertEquals(urlActual, urlEsperada);
    }

}
