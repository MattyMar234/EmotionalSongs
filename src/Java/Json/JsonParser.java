package Java.Json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONTokener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

    protected String FilePath;
    protected JSONObject Jsonobject;

    //private LinkedHashMap<String, Object> DataStructure;
    private final boolean termianlOutput = true;

    public JsonParser(String Path)  {
        this.FilePath = Path;
    }


    

    //legge il file json e restituisce il contenuto in strnga
    private Object OpenFile() throws IOException, ParseException 
    {
        File file = new File(this.FilePath);
        
        /*FileInputStream readingFIle = new FileInputStream(file);
        String FILE_DATA = "";
        boolean startvalue = false;
        long index = 0;
        long bytes = file.length();

        InputStream inputStream = new FileInputStream(file);
        JSONTokener tokener = new JSONTokener(inputStream);

        System.out.println(tokener);*/

        FileReader reader = new FileReader(this.FilePath);
        JSONParser parser = new JSONParser();
        Object p = parser.parse(reader);
        


        /*while(readingFIle.available() > 0) {
            System.out.println("index: " + index++ + " of " + bytes);
            char ch = (char)readingFIle.read();

            if(ch != '\n' || ch != '\t' || ch != '\r') {
                
                //per non aggiungere gli spazi
                if(ch == '\'')
                    startvalue = !startvalue;

                if(startvalue || ch != ' ')
                    FILE_DATA += ch;
                
            }
        }*/

        //readingFIle.close();
        return p;
    }

    public void WriteJsonFile(Object data) throws IOException
    {
        FileWriter file = new FileWriter(this.FilePath);
        String DataStructure = "";
        JSONObject [] dataList;

        //inizializzo l'array
        if(data == null) {
            return;
        } 

        if(data instanceof JSONArray) 
        {
            JSONArray jsonarray = ((JSONArray) data);
            dataList = new JSONObject[jsonarray.size()];

            for(int  i = 0; i < dataList.length; i++) {
                dataList[i] = (JSONObject) jsonarray.get(i);
            }

            file.write("[\n");
            for(int k = 0; k < dataList.length; k++) 
            {
                String s = new String();
                s = "\t" + ((JSONObject)jsonarray.get(k)).toJSONString() + (k == dataList.length - 1 ? "\n" : ",\n"); 
                
                for(int  i = 0; i < s.length(); i++) {
                    file.append(s.charAt(i));
                }

                
            }
            file.write("]");
            file.close();

        }
        else if(data instanceof JSONObject) {
            dataList = new JSONObject[1];
            dataList[0] = ((JSONObject) data);

            file.write("[\n\t" + dataList[0].toJSONString() + "\n]");
            file.close();
           

        }
    
    }

    

    public void WriteJsonFile_old(Object data) throws IOException
    {
        FileWriter file = new FileWriter(this.FilePath);
        String DataStructure = "";

        if(data instanceof JSONArray) {
            DataStructure = ((JSONArray) data).toJSONString(); 
        }
        else if(data instanceof JSONObject){
            DataStructure = ((JSONObject) data).toJSONString(); 
        }

        
        //aggiungo L'indentazione
        int LivelloIndentazione = 0;
        String indentedData = new String("");
        final boolean inLine = false;

        file.write("");

        if(!inLine) {
            for(int i = 0; i < DataStructure.length(); i++) 
            {
                indentedData = "";
                switch(DataStructure.charAt(i)) 
                {
                    case '{' : LivelloIndentazione++;  indentedData = indentedData + DataStructure.charAt(i) + '\n' + MakeTab(LivelloIndentazione);  break;
                    case '[' : LivelloIndentazione++;  indentedData = indentedData + DataStructure.charAt(i) + '\n' + MakeTab(LivelloIndentazione);  break;
                    case ']' : LivelloIndentazione--;  indentedData = indentedData + '\n' + MakeTab(LivelloIndentazione) + DataStructure.charAt(i);  break;
                    case '}' : LivelloIndentazione--;  indentedData = indentedData + '\n' + MakeTab(LivelloIndentazione) + DataStructure.charAt(i);  break;
                    case ',' :                         indentedData = indentedData + DataStructure.charAt(i) + '\n' + MakeTab(LivelloIndentazione);  break;
                    case ':' :                         indentedData = indentedData + ' ' + DataStructure.charAt(i) + ' ';   break;
                    default: indentedData += DataStructure.charAt(i);  break;
                }

                file.append(indentedData);
            }
        }
        else {
            //da sistemare
            for(int i = 0; i < DataStructure.length(); i++) 
            {
                indentedData = "";
                switch(DataStructure.charAt(i)) 
                {
                    case '{' : LivelloIndentazione++;  indentedData = indentedData + DataStructure.charAt(i);  break;
                    case '}' : 
                
                        if(LivelloIndentazione-- == 1) { 
                            indentedData = indentedData + DataStructure.charAt(i); 

                            if(i + 2 < DataStructure.length()) {
                                indentedData += ",";
                                i++; 
                            }

                            indentedData  += '\n';
                    
                        }  break;
                
                    default: indentedData += DataStructure.charAt(i);  break;
                }

                file.append(indentedData);
            }
        }

        file.flush();
        file.close();
    }

    public boolean updateObject(JSONObject input) {

        //ObjectMapper mapper = new ObjectMapper();
        return true;
    }

    public JSONObject ReadJsonFile_as_JsonObject() throws ParseException, IOException 
    { 
        JSONParser parser = new JSONParser();
        Object obj = OpenFile();

        if(obj instanceof JSONObject) {
            JSONObject object = (JSONObject) obj;
            return object;
        }

        return null;
    }
    
    public JSONArray ReadJsonFile_as_JsonArray() throws ParseException, IOException 
    {
        JSONParser parser = new JSONParser();
        Object obj = OpenFile();

        if(obj instanceof JSONArray) {
            JSONArray objects = (JSONArray) obj;
            return objects;
        }
        
        return null;
    }

    public JSONObject [] ReadJsonFile_as_ArrayOfJsonObject() throws ParseException, IOException 
    {
        JSONParser parser = new JSONParser();
        Object obj = OpenFile();
        JSONObject output [];

        if(obj instanceof JSONArray) {
            JSONArray objects = (JSONArray) obj;

            output = new JSONObject [objects.size()];
            
            for(int  i = 0; i < output.length; i++) {
                output[i] = (JSONObject) objects.get(i);
            }

            return output;
        }
        return null;
    }

    //legge il file json (string) e restituisce una hashmap con tutti i dati del file
    public LinkedHashMap<String, Object> ReadJsonFile()
    {
        JSONParser parser = new JSONParser();
        LinkedHashMap<String, Object> Data = new LinkedHashMap<String, Object>();

        try {  
            JSONObject object = (JSONObject) OpenFile();
            //if(termianlOutput) System.out.println(object);
            Data = LoadData(object);

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
    private String MakeTab(int n) {
        String v = "";
        for(int i = 0; i < n; i++){
            v += "\t";
        }
        return v;
    }
}
