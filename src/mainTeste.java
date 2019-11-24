import model.DAO.UsuarioDAO;

public class mainTeste {

	public static void main(String[] args) {
		UsuarioDAO u = new UsuarioDAO();
		boolean teste = u.confereLogin("ariadne", "123");
		System.out.println(teste);
	}
}
