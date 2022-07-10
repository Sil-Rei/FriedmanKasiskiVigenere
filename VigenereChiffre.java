import java.util.*;

public class VigenereChiffre {

    public static Map<String, Integer> getFrequencyDistribution(String text){
        Map<String, Integer> dic = new TreeMap<>();
        for(String chr : text.split("")){
            dic.merge(chr, 1, Integer::sum);
        }
        return dic;
    }

    public static double friedmann(String text){
        Map<String, Integer> dic = getFrequencyDistribution(text);
        int n = text.length();
        double i_chiffre = 0;
        double i_random = 0.0385;
        double i_german = 0.0762;

        for(int value : dic.values()){
            i_chiffre += value * (value-1);
        }
        i_chiffre *= 1.0 /(n*(n-1));

        return (n*(i_german - i_random))/(i_chiffre * (n-1) + i_german - n * i_random);
    }

    public static void kasisky(String text) {
        /*
        textSegments trackt die jeweiligen 3er Blöcke und ihre letzte Position. distances die 3er Blöcke und ihre Distanzen
        Der Text wird in 3er Blöcken abgescannt, nicht bereits gespeicherte Blöcke werden hinzugefügt und die Position gespeichert.
        Sollte ein Block bereits dokumentiert sein, wird der Block distances hinzugefügt und die Position in textSegments aktualisiert.
         */
        Map<String, Integer> textSegments = new HashMap<>();
        Map<String, List<Integer>> distances = new HashMap<>();

        for(int i = 0; i < text.length()-2; i++){
            String subString = text.substring(i, i+3);

            if(!textSegments.containsKey(subString)){
                textSegments.put(subString, i);
                distances.put(subString, new ArrayList<>());
            }else{
                distances.get(subString).add(i - textSegments.get(subString));

                textSegments.put(subString, i);
            }
        }
        for (String key : distances.keySet()){
            if(!distances.get(key).isEmpty() && distances.get(key).size() >1){
                //System.out.println(key + distances.get(key));
            }
        }

        Map<Integer, Integer> divisors = new TreeMap<>();

        for(List<Integer> lst: distances.values()){
            for(Integer distance : lst){
                for(int i = 3; i < 20; i++){
                    if(distance % i == 0){
                        divisors.merge(i, 1, Integer::sum);
                    }
                }
            }
        }
        System.out.println(divisors);
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(divisors.entrySet());
        list.sort(Map.Entry.comparingByValue());
        System.out.println(list);

        System.out.println("most likely key lengths: " + list.get(list.size()-1) + " or " + list.get(list.size()-2));
    }


    public static void main(String[] args) {
        String text = "CVJTNAFENMCDMKBXFSTKLHGSOJWHOFUISFYFBEXEINFIMAYSSDYYIJNPWTOKFRHWVWTZFXHLUYUMSGVDURBWBIVXFAFMYFYXPIGBHWIFHHOJBEXAUNFIYLJWDKNHGAOVBHHGVINAULZFOFUQCVFBYNFTYGMMSVGXCFZFOKQATUIFUFERQTEWZFOKMWOJYLNZBKSHOEBPNAYTFKNXLBVUAXCXUYYKYTFRHRCFUYCLUKTVGUFQBESWYSSWLBYFEFZVUWTRLLNGIZGBMSZKBTNTSLNNMDPMYMIUBVMTLOBJHHFWTJNAUFIZMBZLIVHMBSUWLBYFEUYFUFENBRVJVKOLLGTVUZUAOJNVUWTRLMBATZMFSSOJQXLFPKNAULJCIOYVDRYLUJMVMLVMUKBTNAMFPXXJPDYFIJFYUWSGVIUMBWSTUXMSSNYKYDJMCGASOUXBYSMCMEUNFJNAUFUYUMWSFJUKQWSVXXUVUFFBPWBCFYLWFDYGUKDRYLUJMFPXXEFZQXYHGFLACEBJBXQSTWIKNMORNXCJFAIBWWBKCMUKIVQTMNBCCTHLJYIGIMSYCFVMURMAYOBJUFVAUZINMATCYPBANKBXLWJJNXUJTWIKBATCIOYBPPZHLZJJZHLLVEYAIFPLLYIJIZMOUDPLLTHVEVUMBXPIBBMSNSCMCGONBHCKIVLXMGCRMXNZBKQHODESYTVGOUGTHAGRHRMHFREYIJIZGAUNFZIYZWOUYWQZPZMAYJFJIKOVFKBTNOPLFWHGUSYTLGNRHBZSOPMIYSLWIKBANYUOYAPWZXHVFUQAIATYYKYKPMCEYLIRNPCDMEIMFGWVBBMUPLHMLQJWUGSKQVUDZGSYCFBSWVCHZXFEXXXAQROLYXPIUKYHMPNAYFOFHXBSWVCHZXFEXXXAIRPXXGOVHHGGSVNHWSFJUKNZBESHOKIRFEXGUFVKOLVJNAYIVVMMCGOFZACKEVUMBATVHKIDMVXBHLIVWTJAUFFACKHCIKSFPKYQNWOLUMYVXYYKYAOYYPUKXFLMBQOFLACKPWZXHUFJYGZGSTYWZGSNBBWZIVMNZXFIYWXWBKBAYJFTIFYKIZMUIVZDINLFFUVRGSSBUGNGOPQAILIFOZBZFYUWHGIRHWCFIZMWYSUYMAUDMIYVYAWVNAYTFEYYCLPWBBMVZZHZUHMRWXCFUYYVIENFHPYSMKBTMOIZWAIXZFOLBSMCHHNOJKBMBATZXXJSSKNAULBJCLFWXDSUYKUCIOYJGFLMBWHFIWIXSFGXCZBMYMBWTRGXXSHXYKZGSDSLYDGNBXHAUJBTFDQCYTMWNPWHOFUISMIFFVXFSVFRNA";

        Map<String, Integer> dic = getFrequencyDistribution(text);


        System.out.println("Friedmann: " + friedmann(text));
        kasisky(text);

    }
}
