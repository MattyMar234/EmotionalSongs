package JsonFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Json {

    protected String FilePath;
    protected JSONObject Jsonobject;

    //private LinkedHashMap<String, Object> DataStructure;
    private final boolean termianlOutput = true;

    public Json(String Path)  {
        this.FilePath = Path;
    }

    //legge il file json e restituisce il contenuto in strnga
    private String OpenFile() throws IOException 
    {
        if(termianlOutput) System.out.println("read file " + this.FilePath);
        String FILE_DATA = "";

        
        FileInputStream readingFIle = new FileInputStream(new File(this.FilePath));
        
        while(readingFIle.available() > 0) {
            FILE_DATA += (char)readingFIle.read();
        }

        readingFIle.close();

        return FILE_DATA;
    }

    //legge il file json (string) e restituisce una hashmap con tutti i dati del file
    public LinkedHashMap<String, Object> ReadJsonFile()
    {
        JSONParser parser = new JSONParser();
        LinkedHashMap<String, Object> Data = new LinkedHashMap<String, Object>();

        try {  
            JSONObject object = (JSONObject) parser.parse(OpenFile());
            if(termianlOutput) System.out.println(object);
            Data = LoadData(object);

            //per invertire gli elementi
            /*String [] key = Json.getKeys(Data);

            for(int i = 0; i <= key.length/2; i += 2) {
                Object O0 = Data.get(key[i]);
                Object O1 = Data.get(key[i + 1]);

                Data.replace(key[i], O1);
                Data.replace(key[i + 1], O0);
            }*/
        } catch (FileNotFoundException e) {
            if(termianlOutput) System.out.println("Json File not found");
            e.printStackTrace();

        } catch (IOException e) {
            if(termianlOutput) System.out.println("Reading Error");
            e.printStackTrace();

        } catch (ParseException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
            
        } finally {
            if(termianlOutput) System.out.println("reading completed");    
        }
        return Data;
    }

    //crea e concatena le hashmap
    private LinkedHashMap<String, Object> LoadData (JSONObject json) 
    {
        LinkedHashMap<String, Object> outputMap = new LinkedHashMap<String, Object>();
        String key;
        Object obj;


        for (Iterator iterator = json.keySet().iterator(); iterator.hasNext(); ) {
            key = (String) iterator.next();

            try {
                obj = json.get(key);

                if(obj instanceof JSONObject) {
                    Map<String, Object> val = LoadData(getElements(json, key));
                    outputMap.put(key, val);
                }
                else {
                    Object val = json.get(key);
                    outputMap.put(key, val);
                }

            } 
            catch(Exception e) {
                System.out.println(e);
            }
        }
        return outputMap;
    }

    private JSONObject getElements(JSONObject json, String Key) 
    { 
        if(json.containsKey(Key)) 
        {
            Object simpleObject = json.get(Key);

            if(simpleObject instanceof JSONObject) {
                return (JSONObject) simpleObject;
            }
        }   
        return null;      
    }

    public String BuildJsonFile(LinkedHashMap<String, Object> map, int deep)
    {
        String FileContent = "";
        String [] keys = getKeys(map);

        if(deep == 0) FileContent += "{\n";

        for(int i = 0; i < keys.length; i++) {
            FileContent += "\n" + MakeTab(deep + 1) + "\"" + keys[i] + "\" : ";

            if(map.get(keys[i]) instanceof Map || map.get(keys[i]) instanceof LinkedHashMap) 
            {
                FileContent += MakeTab(deep + 1) + "{\n";
                FileContent += BuildJsonFile((LinkedHashMap) map.get(keys[i]),deep + 1);
                FileContent += "\n" + MakeTab(deep + 1) + "}";

                if(i != keys.length - 1)FileContent += ",\n";
            
            }else {
                FileContent += "\"" + (String)map.get(keys[i]) + "\"";
                if(i != keys.length - 1)FileContent += ",";
            }
        }
        if(deep == 0) FileContent += "\n}";
        return FileContent;
    }

    


    /*================================ utility ===============================*/
    
    // resituisce un array di stringe contenente tutte le key dell'hashmap 
    // passata come parametro
    public static String[] getKeys(LinkedHashMap<String, Object> map) 
    {
        Set<String> keys = map.keySet();
        String [] key = new String[map.size()];
 
        int counter = 0; 
        for(String key_ : keys) {
            key[counter++] = key_;
        }
        return key;
    }

    //restituisce l'oggetto specificato dal percorso conetenuto nella struttura
    @SuppressWarnings("unchecked")
    public static Object GetElement(LinkedHashMap<String, Object> map, List<String> path) 
    {
        String key = path.get(0);

        //verifico se esiste l'elemento
        if(!map.containsKey(key)) return null;

        //verifico se tale chiave rappresenta una sotto-struttura
        if(map.get(key) instanceof Map) 
        {
            //se sono alla fine, ritorno la sotto-struttura
            if(path.size() == 1) return map.get(key);

            //altrimenti righiamo questa funzione con la sotto-struttura, incrementando l'indice del percorso
            path.remove(0);
            return GetElement((LinkedHashMap<String, Object>) map.get(key), path);            
        }
        return map.get(key);
    }

    //crea la tabulazione
    private static String MakeTab(int n) {
        String v = "";
        for(int i = 0; i < n; i++){
            v += "\t";
        }
        return v;
    }
}