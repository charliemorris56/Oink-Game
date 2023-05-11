package com.example.oink

import android.Manifest
import android.app.DownloadManager.Request
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.speech.RecognizerIntent
import android.util.JsonReader
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.squareup.okhttp.Call
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Response
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.File
import java.io.IOException

class PlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        PermissionCheck();
        AnimalRandomiser();

//        val debugButtonClick = findViewById<Button>(R.id.debugButton)
//        debugButtonClick.setOnClickListener {
//            //Timer();
//            //GetRhymeWords();
//            //Timer();
//            SpeechToTextAnimalChecker();
//        }

        val homeButtonClick = findViewById<Button>(R.id.homeButton)
        homeButtonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }

//        val speechToTextButton = findViewById<Button>(R.id.speechToTextButton)
//        speechToTextButton.setOnClickListener {
//            StartSpeechToText();
//        }

        var animalText = findViewById<TextView>(R.id.animalText);
        animalText.text = animalName;
        SetAnimalImage();

        Timer();
    }

    private val timerLengthSeconds = 2;
    private val timerIntervalSeconds = 1;

    private val RESULT_SPEECH = 1;
    private var outputText: String? = "";

    private var animalName = "";
    private val animalNameSoundMap = mapOf<String,String>("Cow" to "Moo", "Pig" to "Oink", "Sheep" to "Bah");

    private var rhymeNum = 200; //Max size 1000
    private var animalNoiseRhymeSounds: String? = "Null";
    private var animalNoiseRhymeSoundsCow: String = "moo mu mou moue hmu muw May Ma mi My mo Mao Mai Mae Mau Mei Moe mea mow mee maw mah mie Miao maa Mma moy moh Hmi Mey meu Meaux Mmi Muh meh Hma ndma Maye mih Moye Mmo mye mawe mowe Mrw mmy Mrh miy miw Mrx Myh meeh maih maah miee hmy myy Mauw Meaw Macx Hmie -mo .my mes- my- New knew nu gnu noo nou nux ngu nue Mnu Nnu pneu Pnu Nuw Nuww Gnew nu- me move moon mood moose mgm moot mew amu moos mieux mousse Msu Mui Muth mlu Smu Moog imu " +
            "Mmu Miu Mool omoo Nmu moone umu mouche mue mooch mooi mgu Mawu moong omu Mru Mdu muu mooed mouille Mbu lmu Rmu muis Moock Moote amou mouette Jmu mooo shmoo emeu muise Mewe merou moom Mootw mooie Mhu smoo Mooz muegge moues mooc Aamu schmoo Moop aimu moof muie Mucc Miyu moob Muah Ohmu me-too you who now know no u Na U. knee ne Eu Hugh ni nay Wu yu Hu nigh nee Iu Nih Woo Nye nie neu yew ewe Nai nah Nei hou noi hoo naw Nao noe ney Naa noh yuh Thu ioo whew Weu " +
            "Neh yue gnaw yoo Nau Mne neigh Uwe nae yueh Ooooo eeuw nii noy Mno Knowe Iue Eiu nuh hno kno Kna Huw whoo nna nni aauw gna oue ayu nais Eeu whois Aew uou thew wuh uoo eue wou Auo Noix owu Aou eoo nno yeu noie Hna Uuu ihu nej kni Knaw Wew Auew Gno yuu Iuu nny Knie Naud whoe Eou niw uhu Hooe uhuh whu Neau Uew auu euh mny uue Houx Uiu ayuh Eww gny pny Hooh Uww Oew Eugh Aeeu a.u wuu Naeh ouh Gnau Wuw Ewh pnea Uuh hny nahh eyu knoe Neault " +
            "gnow gnah knawe gnie yux pnau eu- made mad Mac Emma Amy Ma'am Mack mach Ami Ama mace Dma Emi Mab Hmo Hmong Madge Irma Emmy ammo maas ima Imo Aimee amma mache amo Ema Imai Csma Bma Amoy imi maar Macc emo Erma Lma amie maat Dmi Maass Gma Emmie Maan amah amae Ammi Lmi Maag Almy M.i immo Cmmi Jma Gmi emy Gnma Fma Madd Hmas immi Imma Lmo Macke maam Maal Mabe Aama Maciej Aami hmis aame Amey Emmi Eimi Madh Amai aima maak Maack Emei Jmi Hkma imy Amaa Dmaa Amei Aimo Amee dtma maad maaa " +
            "dmae Emigh Ahma Auma M.o Aimi Jmo ammah Hmic maaf Imei aimai Maap Hhmi Imta Maac Macg amoi Knmi Imao iima Ahmo Emmo maai Irmo dmma imaa Maed Awma bhma Maab Eema Mabb lmy Ahmi amoe Lmaa Dhmo Cmma maath hmod gmy m i maang Emoe lmao m e aim high m o m a -man -mas -mer -mate -men -oma -mad -made -mere .mov gmai mac-";

    private var animalNoiseRhymeSoundsPig: String = "onik bike mike wait ike boink Loinc boinc Koike oik paranoic Hoik Hoick beuc Boik Boike Stroik oinked Oicc Oick oyc ojk boinked boinks roicc vanschoick point joint joined coins in point appoint joins coined rejoined enjoined disappoint Moines conjoined loins moins disjoint pointe conjoint adjoint adjoined anoint enjoins adjoins rejoins Purloined Lapointe groins quoins disjoined conjoins groynes oint groined Joynt poins reappoint Pierpoint goins soigne " +
            "joynes joines goines uncoined unjoined disjoins rejoint Lapoint koines talapoins noint coigns purloins aroint loynd coigne poigne foins poind roint foined interjoined unjoint goynes .point aroynt Burgoyne's caroigne coin's cv joint disanoint eloigned enoint injoint macedoines mss point poynd preappoint soyned tm joint join coin goin des moines groin phenytoin doin rejoin loin Jeune Burgoyne enjoin Bowdoin Coyne Boyne enc adjoin Lemoine koine thunk ankh conjoin Moine Eoin Lemoyne " +
            "moin soin oin Beaudoin Cloyne Teun Baudoin quoin Aucoin groyne Enq Bourgoin purloin Chanoine broin macedoine coign foin Royne Feucht Evenk wonk Hoyne Mcgloin Alboin koin Ardoin toin Ancc boin Mohnke aloin avoine voin Chicoine roin Jodoin proin Eloin Boyn oine Goyne recoin oiks ganoine chaudoin Antimoine Scheune mccoin Unq Leuchte Boudoin Micoin unjoin surloin raboin injoin hoicks c-in-c disloign furoin interjoin leoine misjoin reconjoin sardoin soign twonk underjoin Voip Doig Oig " +
            "Roig Oip deug Boyg Moip Oyp Heup coigue Boig Noip Foip Soip reup oyg poig Oitp boip joip loipe mccoig poip think bank Inc. thank drink link Frank rank tank pink blank trunk drunk sink zinc drank ink monk sank sunk flank shrink junk inc Hank brink plank bunk Anc crank wink shrank franc chunk blink Blanc shrunk sync funk Fink punk rethink mink stink in bank shank Planck lnc skunk conch rink Franck yank Unc dank nunc kink donc Banque Leblanc prank in blank Mnc chink hunk Franke Schenck " +
            "in sync cinq antitank Ranke Bentinck clink interbank stank cinque Francke func slunk lank Schenk Grimke Munk dunk manque hanc spank swank Schank Monck tunc honk tonk aunque clank spunk Spink tink Henk slink Enke Hanke dink Finke Strunk debunk linc Henke banc Linke quinque Zink Onc flunk unk minke Fomc clunk bronc Funke Behnke linq bonk plunk Shenk stunk Blanck Finck Blanke Bronk gunk Menke Frink Ongc Jahnke Schunk Wenk skink Inq Cinc outrank Muench Reinke Schwenk Denk Schrank Anke Lenk " +
            "Flink sinc interlink Vink Malinke ank danke Enk Brunk Janke Moench conk Klink banke Schimke Cronk unlink Steinke wank Donk multilink Funck Schrenk Klenk Ranck bink xlink Schinke Vlaminck Wanke Nanc Hink Schenke krank trunc Linck Schlink Zemke plonk Klinck ananke Ramc tronc Ngk Kennebunk Minc Wnc bunke Wenck Tanque Encke Vonk Meinke lnk Benke Pronk Tanke plink Limc sanc Kroenke Swink nwlink Yunque canc Domke Zenk Ziemke Trnc Menc flanc Shunk Arinc twink Onk punc Klinke Lenke delink chank " +
            "trank Wenke relink junc Lanc Mank Renk Anq Fonck Dunc Enck gink Hinck Schwank comc Zinke skank Ronk Amk Manke Montblanc Rinck jink Mahnke Fincke timc Ranc Schwenke Kohnke Bonke Unthank Penk pank fank nke Mnk trink conque klunk Jonc tunk clonk Zank Rinke";

    private var animalNoiseRhymeSoundsSheep: String = "ba bah bha bow bough bao bau Baugh Biao baw Bawe by be b B. boy buy bay bye bee bei bi Bo bu beau Bey boo bea beaux Bai bui bae boi bou bie Boe boh Bih beh bhi beaut bowe booh boye Buh bhu Bew boue buu Baye Beu bho Boie B.e baee biy byi bhoy bouy Byw beeh buw bhy bi- by- Paa bar bought Bob boss bomb Bl Bach bom Bonn Bois burr Barr bot ber paw bog bur pao Baal barre pow bod balm bock bos Bhai Bohm Bir bonne Bok Bahr bop baht blah bol " +
            "Pau bes Piao baas Bott Bhatt Bhat boc Boche Baath bosse Bopp bosh birr Pauw bwr botch ba'ath Bahl brah bhang botte Baade Baan bombe powe baaed Bolle bhag Baar Boq boch Lbh Bobb bhar Pough beur bahais baat bodge baab Bahd Yba bvr baai Bogg Baag baad baap Bahre Baaf blaa Paugh bahs booe Baasch baam bohs braa Lbx bhad h-bomb h-e-b bar- e-bomb h-bar p P. pay pa pi po pie Poe pea pu pee pei pah pooh hah Pye poi ppi poo Ppa ppo Poh haa pou Peau pae paye Pih puh peh Ppy puu poy Peay " +
            "pey paie p.t p.e piy pyi piye puw Hpux Paigh p-a py- ball bore bowed brow bows bout Bauer bower boar bor bohr Bosch boughs Baum Blau baud Balk Baur bong boule Brough bouche bawl Bausch bouse Boff Balkh Baume Boj bawd Blough baulk bawn bour Baule Baul braw Brau Bauch Bouc blaw Baus baun Borre Bouch baut Bhor Blauw boere baug Bhot Bosche borr Bauge bauk bowge bawk boyau baws boughed baos beod beotch be-all -bore back base bad bag bare bath bass ban batch bat abbey Abu bang bake bait bail badge bade B.a. babe bathe bas Abby " +
            "abbe bal A.b. bale abo Baer Bain bane Abba bac bam bab bash bate Abi Abbie Bagh bap bak basse abou Bache Bakke b.c batt Bahn Baeck baru Baier Babb baile baize Bair Basch Baie Abbaye Bann Aby bav backe bais Baehr bave Abie baik Abot Bagge baj Baia balle Baeyer baisse baek baf baig bage Bagg baith Baiae Abbo Baine B.a Aboo Baen abbi bague barreau aaba bame batte baise b.s B.p baff Abya baid baim B.v bafe banh Bahm Aibo Aaby Baille Basw aboi abee bacc Bashe abay bamm Badd Baec B.d B.b baq bahe B.g Bapp Abei " +
            "B.o Baisch balh Baed bacd bangue Baiz bape Abae Bamn abye bakk Baeo Ahba baea baef b e -ble b o b a baffe baro-";

    private val client =  OkHttpClient();

    private fun GetRhymeWords() //API does not return when using unreal words (oink)
    {
        val animalNoise = animalNameSoundMap[animalName];
        val urlQuery = "https://api.datamuse.com/words?rel_rhy=$animalNoise&max=$rhymeNum";
        val request = com.squareup.okhttp.Request.Builder().url(urlQuery).build();

        var rhymeJson: String?;

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(request: com.squareup.okhttp.Request?, e: IOException?) {
                Log.v("APICall", "Error");
                Toast.makeText(applicationContext, "Error: Please make sure you are connected to the internet", Toast.LENGTH_SHORT).show();
            }
            override fun onResponse(response: Response?) {
                rhymeJson = response?.body()?.string();
                rhymeJson?.let { Log.v("APICall", it) };

                animalNoiseRhymeSounds = rhymeJson;
            }
        })
    }

    private fun SetAnimalImage()
    {
        var animalImage = findViewById<ImageView>(R.id.animalImage);

        when (animalName) {
            "Cow" -> {
                animalImage.setImageResource(R.drawable.cow_clean2);
            }
            "Pig" -> {
                animalImage.setImageResource(R.drawable.pig_clean2);
            }
            "Sheep" -> {
                animalImage.setImageResource(R.drawable.sheep_clean2);
            }
        }
    }

    private fun SpeechToTextAnimalChecker()
    {
        when (animalName) {
            "Cow" -> {
                animalNoiseRhymeSounds = animalNoiseRhymeSoundsCow;
            }
            "Pig" -> {
                animalNoiseRhymeSounds = animalNoiseRhymeSoundsPig;
            }
            "Sheep" -> {
                animalNoiseRhymeSounds = animalNoiseRhymeSoundsSheep;
            }
        }

        if (outputText?.let { animalNoiseRhymeSounds?.contains(it.lowercase()) } == true)
        {
            Log.v("TextToSpeech", "Noise is correct");

            val sharedPref = getSharedPreferences("strings.xml", Context.MODE_PRIVATE);
            var animalCollection = "";

            when (animalName)
            {
                "Pig" -> {
                    animalCollection = "animal_collection1";
                }
                "Cow" -> {
                    animalCollection = "animal_collection2";
                }
                "Sheep" -> {
                    animalCollection = "animal_collection3";
                }
            }

            with(sharedPref.edit())
            {
                putInt(animalCollection, 1);
                apply();
            }

            val intent = Intent(this, SuccessActivity::class.java);
            intent.putExtra("Animal", animalName);
            startActivity(intent);
        }
        else
        {
            StartSpeechToText();
            Log.v("TextToSpeech", "Noise is incorrect");
        }
    }

    private fun PermissionCheck()
    {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf("android.permission.RECORD_AUDIO"), 0)
        }
    }

    private fun Timer()
    {
        Log.v("Timer", "Started");
        object : CountDownTimer(
            (timerLengthSeconds * 1000).toLong(),
            (timerIntervalSeconds * 1000).toLong()
        ) {
            override fun onTick(millisUntilFinished: Long) {
                Log.v("Timer", "Tick");
            }

            override fun onFinish() {
                Log.v("Timer", "Done");
                StartSpeechToText();
            }
        }.start()
    }

    private fun AnimalRandomiser()
    {
        var animalNameRan = "";

        do {
            animalNameRan = animalNameSoundMap.keys.random();
        } while (animalNameRan == animalName)

        animalName = animalNameRan;
    }

    private fun StartSpeechToText()
    {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-UK");
        try {
            startActivityForResult(intent, RESULT_SPEECH);
            Log.v("TextToSpeech", "Done");
        } catch (e: ActivityNotFoundException) {
            Log.v("TextToSpeech", "Error");
            e.printStackTrace();
        }
    }

    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_SPEECH)
        {
            if (resultCode == RESULT_OK && data != null)
            {
                val outputTextArray = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                outputText = outputTextArray?.get(0);
                if (outputText?.length!! > 3)
                {
                    outputText = outputText?.take(3);
                }
                if (outputText != null) {
                    Log.v("TextToSpeech", outputText!!)
                };
                //Toast.makeText(applicationContext, outputText, Toast.LENGTH_SHORT).show();
                SpeechToTextAnimalChecker();
            }
        }
    }

}