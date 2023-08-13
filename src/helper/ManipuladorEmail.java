package helper;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
public class ManipuladorEmail {

	public static Properties getProp() throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream("./properties/config_email.properties");
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

	
	public static String getUrl() {
		return pegarProp().getProperty("prop.url_email");
	}
	
	public static String getKey() {
		return pegarProp().getProperty("prop.key");
	}
	
	public static String getFromEmail() {
		return pegarProp().getProperty("prop.from_email");
	}
	
	public static String getFromName() {
		return pegarProp().getProperty("prop.from_name");
	}
	
	public static String getToEmail() {
		return pegarProp().getProperty("prop.to_email");
	}
	
	public static String getToName(){
		return pegarProp().getProperty("prop.to_name");
	}

	
	public static boolean getIsAtivo() {
		String isAtivo = "";
		try {
			isAtivo = getProp().getProperty("prop.is_ativo");
		} catch (IOException e) {
			System.out.println("Valor incorreto, verifique no arquivo de configuração de sms");
			e.printStackTrace();
		}
		
		if (isAtivo.equalsIgnoreCase("true")) {
			return true;
		}
		return false;
	}
	
	
	public static void setIsAtivo(boolean b) {
		Properties props = pegarProp();
		props.setProperty("prop.is_ativo", String.valueOf(b));
		try (OutputStream output = new FileOutputStream("./properties/config_email.properties")) {
			props.store(output, "Configurações do aplicativo");
		} catch (IOException e) {
			System.err.println("Erro ao escrever no arquivo de propriedades: " + e.getMessage());
		}
	}
	
	
			
	
	
}