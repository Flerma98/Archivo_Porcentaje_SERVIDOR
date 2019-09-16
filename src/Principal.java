
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fernando Alberto
 */
public class Principal {

     static int numsocket;
    
    public static void main(String[] args) throws Exception {
        try{
            numsocket= Integer.parseInt(args[args.length-1]);
        } catch (Exception e) {System.out.println("Introdusca un socket valido"); System.exit(0);}
        
        ServerSocket server;
        Socket connection= null;
        try{
          server = new ServerSocket(numsocket);
          while ( true ) {
              try{
          connection = server.accept();
             }catch (Exception e ) {
              System.err.println(e);
             }
          BufferedReader lector=new BufferedReader(new InputStreamReader(connection.getInputStream()));
          PrintWriter escritor=new PrintWriter(connection.getOutputStream(),true);
          String entrada="";
          if((entrada=lector.readLine())!=null){
                System.out.println(entrada);
                EnviarArchivo(entrada, connection, escritor);
                }
        }
        }catch (Exception e ) {
         System.err.println(e);
        }
    }
    
    public static void EnviarArchivo(String direccion, Socket client, PrintWriter escritor) throws FileNotFoundException, IOException{
        final File localFile = new File(direccion);
        long size= localFile.length();
        escritor.println(size);
        if(localFile.exists()){
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(localFile));
        BufferedOutputStream bos = new BufferedOutputStream(client.getOutputStream());
        byte[] byteArray;
            //Enviamos el nombre del fichero
            DataOutputStream dos=new DataOutputStream(client.getOutputStream());
            dos.writeUTF(localFile.getName());
            //Enviamos el fichero
            byteArray = new byte[8192];
            int in;
            while ((in = bis.read(byteArray)) != -1){
                 bos.write(byteArray,0,in);
            }
        bis.close();
        bos.close();
        }else{
            System.out.println("Archivo no encontrado");
        }
    }
    
    
}
