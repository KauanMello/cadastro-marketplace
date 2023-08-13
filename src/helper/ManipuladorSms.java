package helper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class ManipuladorSms {

	public static Properties getProp() throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream("./properties/config_sms.properties");
		props.load(file);
		return props;
	}
	
	private static Properties prop;

	public static Properties pegarProp() {
		try {
			return prop = getProp();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	public static void setIsAtivo(boolean b) {
		Properties props = pegarProp();
		props.setProperty("prop.is_ativo", String.valueOf(b));
		try (OutputStream output = new FileOutputStream("./properties/config_sms.properties")) {
			props.store(output, "Configurações do aplicativo");
		} catch (IOException e) {
			System.err.println("Erro ao escrever no arquivo de propriedades: " + e.getMessage());
		}
	}
	
	public static String getToNumber() {
		return pegarProp().getProperty("prop.to_number");
	}
	
	public static String getFromNumber() {
		return pegarProp().getProperty("prop.from_number");
	}
	
	public static String getUrl() {
		return pegarProp().getProperty("prop.url_sms");
	}
	
	public static String getAccountSsid() {
		return pegarProp().getProperty("prop.account_ssid");
	}
	
	public static String getAuthToken() {
		return pegarProp().getProperty("prop.auth_token");
	}
	
	public static boolean getIsAtivo() {
		String isAtivo = "";
		try {
			isAtivo = getProp().getProperty("prop.is_ativo");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (isAtivo.equalsIgnoreCase("true")) {
			return true;
		}else if (isAtivo.equals("false")) {
			return false;
		}else {
			throw new IllegalArgumentException("valor incorreto");
		}
	}
	
}
