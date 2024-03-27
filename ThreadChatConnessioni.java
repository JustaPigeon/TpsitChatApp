import java.net.*; 
import java.io.*; 

public class ThreadChatConnessioni implements Runnable{
    private ThreadGestioneServizioChat gestoreChat; 

    private Socket client=null;
    private BufferedReader input = null;
    private PrintWriter output = null; 
    Thread me; 

        /**
     * Il costruttore crea e inizializza lo stream di input e output, il gestoreChat ed il Socket client. 
     * @param gs thread che gestisce la chat
     * @param cl socket che contiene la connessione con il client
     */

    public ThreadChatConnessioni(ThreadGestioneServizioChat gs, Socket cl)
    {
        this.gestoreChat = gs; 
        this.client = cl; 
        try{
            this.input = new BufferedReader(new InputStreamReader(client.getInputStream())); 
            this.output = new PrintWriter(this.client.getOutputStream(), true);
        }catch(Exception e)
        {
            output.println("Errore!");
        }
        me=new Thread(this); 
        me.start(); 
    }

    /**
     * Run() aspetta finché la stringa non cambia valore, una volta cambiato valore, viene inviato il messaggio.
     */
    public void run()
    {
        while (true) {
            try{
                String mex = null; 
                while((mex=input.readLine())==null)
                {

                }
                
                gestoreChat.spedisciMessaggio(mex);
            }catch(Exception e)
            {
                output.println("Errore nella spedizione!");
            }
        }
    }

    /**
     * Invia il messaggio ad un singolo client.
     * @param messaggio stringa del messaggio da inviare.
     */
    public void spedisciMessaggioChat(String messaggio)
    {
        try{
            output.println(messaggio);
        }catch(Exception e)
        {
            output.println("Errore spedizione singolo messaggio");
        }
    }
}
