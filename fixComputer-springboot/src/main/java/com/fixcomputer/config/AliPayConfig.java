package com.fixcomputer.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
public class AliPayConfig {
    private String appId;
    private String appPrivateKey;
    private String alipayPublicKey;


    @PostConstruct
    public void init(){
        Config config = new Config();
        config.protocol = "https";

        //沙箱网关
        config.gatewayHost = "openapi-sandbox.dl.alipaydev.com";
        config.signType = "RSA2";
        config.appId = "9021000135623318";
        config.merchantPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCK6I48LXCpYRJsbY+dnXDOnPDqlt8RmshM2Uftr5cS3jTBNBm98EOsv5lTSe4eijd93Am/FADtbXv0jKLqYhG1E1wU4WpjO2jyPBD7uTn4YhdlbSg3B6CUFZjjSpf045B1wzJ2yvILSYCwfzI8Uh+AF+87Isn6taAQw4dml70dnuDyWA9xAqGnCeh+3QHQlvP512B5MRQx4WODI85wb+wZrYneFuTa+79zGCx2Xu3m2tg5OZS8wOowuRQWpP8O5tMe8voJ0M3LgzohmiyZY4Hu0RLztuSScI+VD6f2OOtrH5Dq2y4+qZbk86O2rQj4SpO2uUp9ySAojzd3kHVA9uI1AgMBAAECggEAWP/jkf7zPjLp4PrZl3z2eArp+QQtbFQlncdhZJKqUvZ4CondLGsykQ45qLmnjX21TDidLgYdCgKp9ufcwhuPiNsclmxPA8Fe3kVKBF6LG++NWfj0WT3Tzs1/pbF3Zvoe5rToMmCZcE3p3CvC/g8IC8RKDTOfAbk5E0SUDlGOY9kfmAjK3mfgOpK5Nz1ofGoWHrD7Fy0FViYBQ6mgduqrn0UMeVIgdzYmqkVWqaAxxbKxGLfMlQYdkCmT32o2QpQx1WT+hq2zaM6/nmMPyRV6Amrlv5gUG27k3QQPCqSmVCB+KLUZrWlzBMm3rDceRZ+N9YM76hW2j6eWIbQJ0X9pSQKBgQDl0OxK4pFnK0MGDHDhHvTMJctf2P3UDfYQRnIkXGzjYtcDIAxcufXw4G1h/yyJX6RXBwNlSUSjxf9ioZGFuXRlFFsW/zUg5cfVIgyBN7sXKckw6GY0SZGGcsWveljtC2Q67w+iA+EQXXULn8W0UJqa/Py5aOhw29wCFvPwUHII1wKBgQCavB2zRWZAI3RWL85fFqbun9o+GpWfpNgGz55pBe8LufF2jBsH29JNhx64R4cXcTm06W+lE1xchZSWqZSK+QfVMyr2wpe2RWA79eqeWIqu1XADcFnAB38VjB8yJ3+o939tlSffqnGCy/GTKeuk6Eba4b98A/rcKbRCKQ6tN5MP0wKBgQCru1RnnSkEjEp4YaGUSDqrtu5jwFXlOU2P2eR/kWV5AaokVrWGMRw8lKiIW1OfkA3RTpdV/8tACrwNqyiAYhY9Vlm/NJQx8iNC/D+V69xtsbb7Sh7docASVP+uYmHDOQj8FRp8eiObxAqr1ge596Wgfb4omuFfPJwKH7bH9IVSXwKBgDHVki8xk7vmb0puvSVs0ziZLR3BTB0NZzHSXi9u5M0/BqBqgS4nwvSLJv6ssrp/EdnNMNLWiHGX6AIZ/ahAiiBFCXpNs7TVZSgKjJSQ6aFjRl8lKRKql2hmJ4nspLXTeCvf1Oa5KzO34UbHQohq8p7+A8C6O2n62H5SYNKyyl9XAoGAR53v0di8JoSnO9c8/n+cPkOM24VuhpaQk75jXKn9eoaEtOrPVV7PhSU/szs2+gil5n3qLgp4EeyuOzPGba6jrDPE1leSOOPb++LpGptYlRTma1QJcK2fAiuvXeZn0EjNtMmFsJJ1Jq8l7JIn2Hyy/aCjHSmEP4umdA+kyLIqa4E=";
        config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk8jrdMH5rhiPN0QnQh42WWO1ZgshpvMv3skHKUBFYZLaYOlJj46WqOJcv9IVhYgVfRqrKTsMUK/j+ru3Tkf7KGQ435ZfkfC+tovJX9JVnB0f8xD+Dx6xsrUp2eb6zGUkgM/PP2GxHMbSZ62B0gpLrx35D1D6wwN2leHycGRKMuV1XUzlBos3ICSUD5mfXGr2z7cHfnmpalHMOxB0wm17WXbCtMpXDNR/Gh+E0uiQ7WD5WeqO+mM0n1NgXeRJ8Sdc1p0F2UfxkAp5PnQFO7SFxDAjxkqiL49aTeM5zWxyRDYQwW8/CJ5rJXHweoPxQi6mgJEJMlwqjjdBtmP50jZH6QIDAQAB";
        config.notifyUrl="http://5e383b85.r3.cpolar.top/api/notify";
        Factory.setOptions(config);

    }
}
