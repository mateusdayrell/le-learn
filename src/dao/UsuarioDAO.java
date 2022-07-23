
package dao;

import java.sql.PreparedStatement;
import dto.UsuarioDTO;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import jdbc.ConnectionFactory;
import model.UsuarioMODEL;
import java.util.List;
import java.util.ArrayList;

public class UsuarioDAO {
    private Connection conex;
    
    // metodo construtor
    public UsuarioDAO() {
        this.conex = new ConnectionFactory().conectaBD();
    }
    
    // método para autenticacao de Usuários
    public ResultSet autenticarUsuario(UsuarioDTO objUsuario) {
        // conex = new Conexao().conectaBD(); //REMOVER
        
        try {
            String sql = "select * from usuarios where email = ? and senha = ? ";
            PreparedStatement pstm = conex.prepareStatement(sql);
            pstm.setString(1, objUsuario.getEmail());
            pstm.setString(2, objUsuario.getSenha());
            
            ResultSet rs = pstm.executeQuery();
            return rs;
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "UsuarioDAO: " + error.getMessage());
            return null;
        }
    }
    
    //método para listar usuarios
    public List<UsuarioMODEL> listarUsuarios() {
        try{
            //criar lista
            List<UsuarioMODEL> lista = new ArrayList<>();

            //criar comando sql
            String sql = "select * from usuario";

            //realizar busca no banco de dados
            PreparedStatement pstm = conex.prepareStatement(sql);

            //armazenar resultado em um obj
            ResultSet rs = pstm.executeQuery();

            //atribuindo retorno do bd a um obj do tipo Usuario
            while(rs.next()) {
                UsuarioMODEL obj = new UsuarioMODEL();
                
                obj.setCpf(rs.getString("CPF"));
                obj.setNome(rs.getString("NOME"));
                obj.setEmail(rs.getString("EMAIL"));
                obj.setTelefone(rs.getString("TELEFONE"));
                obj.setTipo(rs.getString("TIPO"));
                obj.setDataNasc(rs.getString("DATANASC"));

                lista.add(obj);
            }

            return lista;
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Erro ao listar usuários. \nUsuarioDAO: " + error.getMessage());
            return null;
        }
    }
    
    //método para cadastrar usuarios
    public void cadastrarUsuario(UsuarioMODEL obj) {
        try {
            //criar o comando SQL
            String sql = "insert into usuario (CPF, NOME, TELEFONE, EMAIL, SENHA, TIPO)" // DATANASC
                        + "values (?, ?, ?, ?, ?, ?)"; // ?
            
            //conectar BD e organizar comando SQL
            PreparedStatement pstm = conex.prepareStatement(sql);
            
            //receber valores do Model (id da ?, valor)
            pstm.setString(1, obj.getCpf());
            pstm.setString(2, obj.getNome());
            pstm.setString(3, obj.getTelefone());
            pstm.setString(4, obj.getEmail());
            pstm.setString(5, obj.getSenha());
            pstm.setString(6, obj.getTipo());
            //pstm.setString(7, obj.getDataNasc());
            
            pstm.execute(); //executar comando
            pstm.close();   //encerrar conexao
            
            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar Usuário. \nUsuarioDAO: " + erro.getMessage());
        }
    }
    
    //método para editar usuários
    public void editarUsuario(UsuarioMODEL obj) {
        try {
            //criar o comando SQL
            String sql = "update usuario set CPF=?, NOME=?, TELEFONE=?, EMAIL=?, SENHA=?, TIPO=?" // , DATANASC=?
                       + "where CPF=?";
            
            //conectar BD e organizar comando SQL
            PreparedStatement pstm = conex.prepareStatement(sql);
            
            //receber valores do Model (id da ?, valor)
            pstm.setString(1, obj.getCpf());
            pstm.setString(2, obj.getNome());
            pstm.setString(3, obj.getTelefone());
            pstm.setString(4, obj.getEmail());
            pstm.setString(5, obj.getSenha());
            pstm.setString(6, obj.getTipo());
            //pstm.setString(7, obj.getDataNasc());
            
            pstm.setString(7, obj.getAntigoCpf());
            
            pstm.execute(); //executar comando
            pstm.close();   //encerrar conexao
            
            JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar Usuário. \nUsuarioDAO: " + erro.getMessage());
        }
    }
    
    //método para excluir usuarios
    public void excluirUsuario(UsuarioMODEL obj) {
        try {
            //criar o comando SQL
            String sql = "delete from usuario where CPF = ?"; // ?
            
            //conectar BD e organizar comando SQL
            PreparedStatement pstm = conex.prepareStatement(sql);
            
            //receber valores do Model (id da ?, valor)
            pstm.setString(1, obj.getCpf());
            
            pstm.execute(); //executar comando
            pstm.close();   //encerrar conexao
            
            JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar Usuário. \nUsuarioDAO: " + erro.getMessage());
        }
    }

}
