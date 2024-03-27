import java.awt.List;
import java.net.*;
import javax.swing.JOptionPane;

public class ThreadGestioneServizioChat implements Runnable
{
    private int nrMaxConnessioni; 
    private List lista; 

    private ThreadChatConnessioni[] listaConnessioni; 

    Thread me; 

    private ServerSocket serverChat; 

    /**
     * Il Metodo Run() si occupa di ricevere le connessioni dei client: al costruttore passiamo come paramentro il numero massimo di connessioni e la lista dei messaggi.
     * @param nr numero connessioni
     * @param lis lista messaggi
     */


    public ThreadGestioneServizioChat(int nr, List lis)
    {
        this.nrMaxConnessioni = nr-1; 
        this.lista = lis; 
        this.listaConnessioni = new ThreadChatConnessioni[this.nrMaxConnessioni];
        me = new Thread(this); 
        me.start();
    }

    /**
     * Run() si occupa di ricevere le connessioni e crea un socket sulla porta 6789.
    */

    public void run()
    {
        boolean continua = true; 
        try{
            serverChat=new ServerSocket(7011); 
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Impossibile instanziare il server!");
            continua = false;
        }
        if(continua){
            try{
                for(int xx = 0; xx<nrMaxConnessioni; xx++)
                {
                    Socket tempo=null; 
                    tempo = serverChat.accept(); 
                    listaConnessioni[xx] = new ThreadChatConnessioni(this, tempo); 
                }
                serverChat.close(); 
            }catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Impossibile instanziare il server chat!");
            }

        }
    }

    /**
     * Si occupa di inviare un messaggio a tutti i client e memorizza l'elenco delle connessioni nell'array listaConnessioni. 
     * @param mex stringa del messaggio
     */

    public void spedisciMessaggio(String mex)
    {
        lista.add(mex);
        lista.select(lista.getItemCount()-1); 
        for(int xx = 0; xx<this.nrMaxConnessioni; xx++)
        {
            if(listaConnessioni[xx]!=null)
            {
                listaConnessioni[xx].spedisciMessaggioChat(mex);
            }
        }
    }
}