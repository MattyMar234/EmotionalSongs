package Java.Managers;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Java.DataClasses.Common;
import Java.DataClasses.Province;
import Java.DataClasses.Region;
import Java.emotionalsongs.EmotionalSongs;


/**
 * Questa classe estende la classe Manager e viene utilizzata per la ricerca dei dati utilizzati in fase di inscrizione di un nuovo utente.
 */
public class LocationsManager extends Manager <Region> {

    /**
    * Crea un oggeto per la gestione dei dati inerenti alle regioni del paese.
    * @param Path Percorso del file da cui si prelevano i dati.
    * @param main Riferimento alla classe Main.
    */
    public LocationsManager(String path, EmotionalSongs main) {
        super(path, main);
    }

    
    /** 
     * Cerca in quale regione è situate la provincia che si sta cercando.
     * @param Name nome della regione.
     * @return ritorna l'oggetto che rappresenta tale regione. Se il nome passato non è valido, il risultato sarà NULL.
     */
    public Province FindProvince(String Name) {
        for(Region region : Data) {

            Province province = region.findProvince(Name);

            if(province != null) {
                return province;
            }
        }

        return null;
    }


    
    /** 
     * Carica i dati nel Manager
     * @return Restituisce TRUE se l'operazione di caricamento va a buon fine altrimenti FALSE.
     */
    public boolean LoadData() 
    {
        System.out.println("reading file " + this.FilePath);

        try {  
            JSONObject [] structure = readJsonData();

            for(JSONObject jsonData : structure) 
            {
                String name = (String)jsonData.get("nome");
                JSONArray caps = ((JSONArray)jsonData.get("cap"));
                String [] cap = new String[caps.size()];

                for(int i = 0; i < caps.size(); i++) {
                    cap[i] = (String) caps.get(i);
                }
                
                Common common = new Common(name, cap);
                Region region = null;
                Province province = null;

                String regionName   = (String)((JSONObject)jsonData.get("regione")).get("nome");
                String provinceName = (String)((JSONObject)jsonData.get("provincia")).get("nome");
    
                

                //cerco la regione e se non è presente la creo
                for(Region r : Data) {
                    if(r.getName().equals(regionName)) {
                        region = r;
                    }
                }

                if(region == null) {
                    region = new Region(regionName);
                    Data.add(region);
                    //System.out.println("new region: " + regionName);
                }


                //cerco la provincia e se non è presente la creo
                province = region.findProvince(provinceName);
                
                if(province == null) {
                    province = new Province(provinceName);
                    region.add(province);
                    //System.out.println("new province: " + provinceName);
                }

                province.add(common);
                //System.out.println("add " + common.getName() + " in " + regionName + ", " + provinceName);

            }

        } catch (FileNotFoundException e) {
            System.out.println("Json File not found");
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("Reading Error");
            e.printStackTrace();

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
            
        } finally {
            System.out.println("reading completed");    
        }
        return true;
    }
    
}
