package practicahateoas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

public class ClienteRest {

	public static void main(String args[]) throws JSONException {
		
		ClienteRest cr = new  ClienteRest();
		cr.realizarPeticiones();
	}

	public void realizarPeticiones() throws JSONException {

		String urlRepos = "";
		String urlPerfilImage = "";
		String usuario = "PipeGarcia";
		Client client = ClientBuilder.newClient();
		WebTarget request1 = client.target("https://api.github.com/users/"
				+ usuario);
		System.out.println("Info del usuario: " + request1.request(MediaType.APPLICATION_JSON).get(
				String.class));
		String perfilResponse = request1.request(MediaType.APPLICATION_JSON)
				.get(String.class);

		JSONObject jObject = new JSONObject(perfilResponse);
		urlRepos = jObject.getString("repos_url");
		urlPerfilImage = jObject.getString("avatar_url");
		descargarImagenPerfil(urlPerfilImage);
		
		WebTarget request2 = client.target(urlRepos);
		String reposResponse = request2.request(MediaType.APPLICATION_JSON)
				.get(String.class);
		System.out.println("Info de los repositorios: " + reposResponse);
	}
	
	public void descargarImagenPerfil(String urlImagen) {
		try {
			// url de la imagen de perfil
			URL url = new URL(urlImagen);

			// se crea la conexión con la url de la imagen que quiero descargar
			URLConnection urlCon = url.openConnection();

			/*
			 * se copia en memoria el archivo que voy a descargar y se
			 * especifica la ruta donde quiero que se almacene
			 */
			File imagen = new File("C:/Users/JohanDavid/Desktop/imagenPerfil.png");
			InputStream is = urlCon.getInputStream();
			FileOutputStream fos = new FileOutputStream(imagen);

			// Lectura y escritura de bytes de la imagen
			byte[] array = new byte[1000];
			int leido = is.read(array);
			while (leido > 0) {
				fos.write(array, 0, leido);
				leido = is.read(array);
			}
			// cierre de conexion
			is.close();
			fos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
