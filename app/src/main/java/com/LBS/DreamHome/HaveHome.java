package com.LBS.DreamHome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class HaveHome extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference, mStates, mCity, mArea, mPlot;
    private EditText ET1, ET2, ET3, ET4, ET5, ET6, ET7, ET8, ET9, ET10, ET11, ET12, ET13, ET14, ET15, ET16, ET17;
    private CheckBox CB10, CB11, CB12, CB13, CB14, CB15, CB16;
    private String S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S18;
    private ChildEventListener mChildEventListener;
    private MessageAdapter mMessageAdapter;
    private ListView mMessageListView;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private Button btnChoose, btnUpload;
    private String[] cities = {"Bombuflat", "Garacharma", "Port Blair", "Rangat", "Addanki", "Adivivaram", "Adoni", "Aganampudi", "Ajjaram", "Akividu", "Akkarampalle", "Akkayapalle", "Akkireddipalem", "Alampur", "Amalapuram", "Amudalavalasa", "Amur", "Anakapalle", "Anantapur", "Andole", "Atmakur", "Attili", "Avanigadda", "Badepalli", "Badvel", "Balapur", "Bandarulanka", "Banganapalle", "Bapatla", "Bapulapadu", "Belampalli", "Bestavaripeta", "Betamcherla", "Bhattiprolu", "Bhimavaram", "Bhimunipatnam", "Bobbili", "Bombuflat", "Bommuru", "Bugganipalle", "Challapalle", "Chandur", "Chatakonda", "Chemmumiahpet", "Chidiga", "Chilakaluripet", "Chimakurthy", "Chinagadila", "Chinagantyada", "Chinnachawk", "Chintalavalasa", "Chipurupalle", "Chirala", "Chittoor", "Chodavaram", "Choutuppal", "Chunchupalle", "Cuddapah", "Cumbum", "Darnakal", "Dasnapur", "Dauleshwaram", "Dharmavaram", "Dhone", "Dommara Nandyal", "Dowlaiswaram", "East Godavari Dist.", "Eddumailaram", "Edulapuram", "Ekambara kuppam", "Eluru", "Enikapadu", "Fakirtakya", "Farrukhnagar", "Gaddiannaram", "Gajapathinagaram", "Gajularega", "Gajuvaka", "Gannavaram", "Garacharma", "Garimellapadu", "Giddalur", "Godavarikhani", "Gopalapatnam", "Gopalur", "Gorrekunta", "Gudivada", "Gudur", "Guntakal", "Guntur", "Guti", "Hindupur", "Hukumpeta", "Ichchapuram", "Isnapur", "Jaggayyapeta", "Jallaram Kamanpur", "Jammalamadugu", "Jangampalli", "Jarjapupeta", "Kadiri", "Kaikalur", "Kakinada", "Kallur", "Kalyandurg", "Kamalapuram", "Kamareddi", "Kanapaka", "Kanigiri", "Kanithi", "Kankipadu", "Kantabamsuguda", "Kanuru", "Karnul", "Katheru", "Kavali", "Kazipet", "Khanapuram Haveli", "Kodar", "Kollapur", "Kondapalem", "Kondapalle", "Kondukur", "Kosgi", "Kothavalasa", "Kottapalli", "Kovur", "Kovurpalle", "Kovvur", "Krishna", "Kuppam", "Kurmannapalem", "Kurnool", "Lakshettipet", "Lalbahadur Nagar", "Machavaram", "Macherla", "Machilipatnam", "Madanapalle", "Madaram", "Madhuravada", "Madikonda", "Madugule", "Mahabubnagar", "Mahbubabad", "Malkajgiri", "Mamilapalle", "Mancheral", "Mandapeta", "Mandasa", "Mangalagiri", "Manthani", "Markapur", "Marturu", "Metpalli", "Mindi", "Mirpet", "Moragudi", "Mothugudam", "Nagari", "Nagireddipalle", "Nandigama", "Nandikotkur", "Nandyal", "Narasannapeta", "Narasapur", "Narasaraopet", "Narayanavanam", "Narsapur", "Narsingi", "Narsipatnam", "Naspur", "Nathayyapalem", "Nayudupeta", "Nelimaria", "Nellore", "Nidadavole", "Nuzvid", "Omerkhan daira", "Ongole", "Osmania University", "Pakala", "Palakole", "Palakurthi", "Palasa", "Palempalle", "Palkonda", "Palmaner", "Pamur", "Panjim", "Papampeta", "Parasamba", "Parvatipuram", "Patancheru", "Payakaraopet", "Pedagantyada", "Pedana", "Peddapuram", "Pendurthi", "Penugonda", "Penukonda", "Phirangipuram", "Pithapuram", "Ponnur", "Port Blair", "Pothinamallayyapalem", "Prakasam", "Prasadampadu", "Prasantinilayam", "Proddatur", "Pulivendla", "Punganuru", "Puttur", "Qutubullapur", "Rajahmundry", "Rajamahendri", "Rajampet", "Rajendranagar", "Rajoli", "Ramachandrapuram", "Ramanayyapeta", "Ramapuram", "Ramarajupalli", "Ramavarappadu", "Rameswaram", "Rampachodavaram", "Ravulapalam", "Rayachoti", "Rayadrug", "Razam", "Razole", "Renigunta", "Repalle", "Rishikonda", "Salur", "Samalkot", "Sattenapalle", "Seetharampuram", "Serilungampalle", "Shankarampet", "Shar", "Singarayakonda", "Sirpur", "Sirsilla", "Sompeta", "Sriharikota", "Srikakulam", "Srikalahasti", "Sriramnagar", "Sriramsagar", "Srisailam", "Srisailamgudem Devasthanam", "Sulurpeta", "Suriapet", "Suryaraopet", "Tadepalle", "Tadepalligudem", "Tadpatri", "Tallapalle", "Tanuku", "Tekkali", "Tenali", "Tigalapahad", "Tiruchanur", "Tirumala", "Tirupati", "Tirvuru", "Trimulgherry", "Tuni", "Turangi", "Ukkayapalli", "Ukkunagaram", "Uppal Kalan", "Upper Sileru", "Uravakonda", "Vadlapudi", "Vaparala", "Vemalwada", "Venkatagiri", "Venkatapuram", "Vepagunta", "Vetapalem", "Vijayapuri", "Vijayapuri South", "Vijayawada", "Vinukonda", "Visakhapatnam", "Vizianagaram", "Vuyyuru", "Wanparti", "West Godavari Dist.", "Yadagirigutta", "Yarada", "Yellamanchili", "Yemmiganur", "Yenamalakudru", "Yendada", "Yerraguntla", "Along", "Basar", "Bondila", "Changlang", "Daporijo", "Deomali", "Itanagar", "Jairampur", "Khonsa", "Naharlagun", "Namsai", "Pasighat", "Roing", "Seppa", "Tawang", "Tezu", "Ziro", "Abhayapuri", "Ambikapur", "Amguri", "Anand Nagar", "Badarpur", "Badarpur Railway Town", "Bahbari Gaon", "Bamun Sualkuchi", "Barbari", "Barpathar", "Barpeta", "Barpeta Road", "Basugaon", "Bihpuria", "Bijni", "Bilasipara", "Biswanath Chariali", "Bohori", "Bokajan", "Bokokhat", "Bongaigaon", "Bongaigaon Petro-chemical Town", "Borgolai", "Chabua", "Chandrapur Bagicha", "Chapar", "Chekonidhara", "Choto Haibor", "Dergaon", "Dharapur", "Dhekiajuli", "Dhemaji", "Dhing", "Dhubri", "Dhuburi", "Dibrugarh", "Digboi", "Digboi Oil Town", "Dimaruguri", "Diphu", "Dispur", "Doboka", "Dokmoka", "Donkamokan", "Duliagaon", "Duliajan", "Duliajan No.1", "Dum Duma", "Durga Nagar", "Gauripur", "Goalpara", "Gohpur", "Golaghat", "Golakganj", "Gossaigaon", "Guwahati", "Haflong", "Hailakandi", "Hamren", "Hauli", "Hauraghat", "Hojai", "Jagiroad", "Jagiroad Paper Mill", "Jogighopa", "Jonai Bazar", "Jorhat", "Kampur Town", "Kamrup", "Kanakpur", "Karimganj", "Kharijapikon", "Kharupetia", "Kochpara", "Kokrajhar", "Kumar Kaibarta Gaon", "Lakhimpur", "Lakhipur", "Lala", "Lanka", "Lido Tikok", "Lido Town", "Lumding", "Lumding Railway Colony", "Mahur", "Maibong", "Majgaon", "Makum", "Mangaldai", "Mankachar", "Margherita", "Mariani", "Marigaon", "Moran", "Moranhat", "Nagaon", "Naharkatia", "Nalbari", "Namrup", "Naubaisa Gaon", "Nazira", "New Bongaigaon Railway Colony", "Niz-Hajo", "North Guwahati", "Numaligarh", "Palasbari", "Panchgram", "Pathsala", "Raha", "Rangapara", "Rangia", "Salakati", "Sapatgram", "Sarthebari", "Sarupathar", "Sarupathar Bengali", "Senchoagaon", "Sibsagar", "Silapathar", "Silchar", "Silchar Part-X", "Sonari", "Sorbhog", "Sualkuchi", "Tangla", "Tezpur", "Tihu", "Tinsukia", "Titabor", "Udalguri", "Umrangso", "Uttar Krishnapur Part-I", "Amarpur", "Ara", "Araria", "Areraj", "Asarganj", "Aurangabad", "Bagaha", "Bahadurganj", "Bairgania", "Bakhtiyarpur", "Banka", "Banmankhi", "Bar Bigha", "Barauli", "Barauni Oil Township", "Barh", "Barhiya", "Bariapur", "Baruni", "Begusarai", "Behea", "Belsand", "Bettiah", "Bhabua", "Bhagalpur", "Bhimnagar", "Bhojpur", "Bihar", "Bihar Sharif", "Bihariganj", "Bikramganj", "Birpur", "Bodh Gaya", "Buxar", "Chakia", "Chanpatia", "Chhapra", "Chhatapur", "Colgong", "Dalsingh Sarai", "Darbhanga", "Daudnagar", "Dehri", "Dhaka", "Dighwara", "Dinapur", "Dinapur Cantonment", "Dumra", "Dumraon", "Fatwa", "Forbesganj", "Gaya", "Gazipur", "Ghoghardiha", "Gogri Jamalpur", "Gopalganj", "Habibpur", "Hajipur", "Hasanpur", "Hazaribagh", "Hilsa", "Hisua", "Islampur", "Jagdispur", "Jahanabad", "Jamalpur", "Jamhaur", "Jamui", "Janakpur Road", "Janpur", "Jaynagar", "Jha Jha", "Jhanjharpur", "Jogbani", "Kanti", "Kasba", "Kataiya", "Katihar", "Khagaria", "Khagaul", "Kharagpur", "Khusrupur", "Kishanganj", "Koath", "Koilwar", "Lakhisarai", "Lalganj", "Lauthaha", "Madhepura", "Madhubani", "Maharajganj", "Mahnar Bazar", "Mairwa", "Makhdumpur", "Maner", "Manihari", "Marhaura", "Masaurhi", "Mirganj", "Mohiuddinagar", "Mokama", "Motihari", "Motipur", "Munger", "Murliganj", "Muzaffarpur", "Nabinagar", "Narkatiaganj", "Nasriganj", "Natwar", "Naugachhia", "Nawada", "Nirmali", "Nokha", "Paharpur", "Patna", "Phulwari", "Piro", "Purnia", "Pusa", "Rafiganj", "Raghunathpur", "Rajgir", "Ramnagar", "Raxaul", "Revelganj", "Rusera", "Sagauli", "Saharsa", "Samastipur", "Sasaram", "Shahpur", "Shaikhpura", "Sherghati", "Shivhar", "Silao", "Sitamarhi", "Siwan", "Sonepur", "Sultanganj", "Supaul", "Teghra", "Tekari", "Thakurganj", "Vaishali", "Waris Aliganj", "Chandigarh", "Ahiwara", "Akaltara", "Ambagarh Chauki", "Ambikapur", "Arang", "Bade Bacheli", "Bagbahara", "Baikunthpur", "Balod", "Baloda", "Baloda Bazar", "Banarsi", "Basna", "Bemetra", "Bhanpuri", "Bhatapara", "Bhatgaon", "Bhilai", "Bilaspur", "Bilha", "Birgaon", "Bodri", "Champa", "Charcha", "Charoda", "Chhuikhadan", "Chirmiri", "Dantewada", "Deori", "Dhamdha", "Dhamtari", "Dharamjaigarh", "Dipka", "Doman Hill Colliery", "Dongargaon", "Dongragarh", "Durg", "Frezarpur", "Gandai", "Gariaband", "Gaurela", "Gelhapani", "Gharghoda", "Gidam", "Gobra Nawapara", "Gogaon", "Hatkachora", "Jagdalpur", "Jamui", "Jashpurnagar", "Jhagrakhand", "Kanker", "Katghora", "Kawardha", "Khairagarh", "Khamhria", "Kharod", "Kharsia", "Khonga Pani", "Kirandu", "Kirandul", "Kohka", "Kondagaon", "Korba", "Korea", "Koria Block", "Kota", "Kumhari", "Kumud Katta", "Kurasia", "Kurud", "Lingiyadih", "Lormi", "Mahasamund", "Mahendragarh", "Mehmand", "Mongra", "Mowa", "Mungeli", "Nailajanjgir", "Namna Kalan", "Naya Baradwar", "Pandariya", "Patan", "Pathalgaon", "Pendra", "Phunderdihari", "Pithora", "Raigarh", "Raipur", "Rajgamar", "Rajhara", "Rajnandgaon", "Ramanuj Ganj", "Ratanpur", "Sakti", "Saraipali", "Sarajpur", "Sarangarh", "Shivrinarayan", "Simga", "Sirgiti", "Takhatpur", "Telgaon", "Tildanewra", "Urla", "Vishrampur", "Amli", "Silvassa", "Daman", "Diu", "Delhi", "New Delhi", "Aldona", "Altinho", "Aquem", "Arpora", "Bambolim", "Bandora", "Bardez", "Benaulim", "Betora", "Bicholim", "Calapor", "Candolim", "Caranzalem", "Carapur", "Chicalim", "Chimbel", "Chinchinim", "Colvale", "Corlim", "Cortalim", "Cuncolim", "Curchorem", "Curti", "Davorlim", "Dona Paula", "Goa", "Guirim", "Jua", "Kalangat", "Kankon", "Kundaim", "Loutulim", "Madgaon", "Mapusa", "Margao", "Margaon", "Miramar", "Morjim", "Mormugao", "Navelim", "Pale", "Panaji", "Parcem", "Parra", "Penha de Franca", "Pernem", "Pilerne", "Pissurlem", "Ponda", "Porvorim", "Quepem", "Queula", "Raia", "Reis Magos", "Salcette", "Saligao", "Sancoale", "Sanguem", "Sanquelim", "Sanvordem", "Sao Jose-de-Areal", "Sattari", "Serula", "Sinquerim", "Siolim", "Taleigao", "Tivim", "Valpoi", "Varca", "Vasco", "Verna", "Abrama", "Adalaj", "Adityana", "Advana", "Ahmedabad", "Ahwa", "Alang", "Ambaji", "Ambaliyasan", "Amod", "Amreli", "Amroli", "Anand", "Andada", "Anjar", "Anklav", "Ankleshwar", "Anklesvar INA", "Antaliya", "Arambhada", "Asarma", "Atul", "Babra", "Bag-e-Firdosh", "Bagasara", "Bahadarpar", "Bajipura", "Bajva", "Balasinor", "Banaskantha", "Bansda", "Bantva", "Bardoli", "Barwala", "Bayad", "Bechar", "Bedi", "Beyt", "Bhachau", "Bhanvad", "Bharuch", "Bharuch INA", "Bhavnagar", "Bhayavadar", "Bhestan", "Bhuj", "Bilimora", "Bilkha", "Billimora", "Bodakdev", "Bodeli", "Bopal", "Boria", "Boriavi", "Borsad", "Botad", "Cambay", "Chaklasi", "Chala", "Chalala", "Chalthan", "Chanasma", "Chandisar", "Chandkheda", "Chanod", "Chaya", "Chenpur", "Chhapi", "Chhaprabhatha", "Chhatral", "Chhota Udepur", "Chikhli", "Chiloda", "Chorvad", "Chotila", "Dabhoi", "Dadara", "Dahod", "Dakor", "Damnagar", "Deesa", "Delvada", "Devgadh Baria", "Devsar", "Dhandhuka", "Dhanera", "Dhangdhra", "Dhansura", "Dharampur", "Dhari", "Dhola", "Dholka", "Dholka Rural", "Dhoraji", "Dhrangadhra", "Dhrol", "Dhuva", "Dhuwaran", "Digvijaygram", "Disa", "Dungar", "Dungarpur", "Dungra", "Dwarka", "Flelanganj", "GSFC Complex", "Gadhda", "Gandevi", "Gandhidham", "Gandhinagar", "Gariadhar", "Ghogha", "Godhra", "Gondal", "Hajira INA", "Halol", "Halvad", "Hansot", "Harij", "Himatnagar", "Ichchhapor", "Idar", "Jafrabad", "Jalalpore", "Jambusar", "Jamjodhpur", "Jamnagar", "Jasdan", "Jawaharnagar", "Jetalsar", "Jetpur", "Jodiya", "Joshipura", "Junagadh", "Kadi", "Kadodara", "Kalavad", "Kali", "Kaliawadi", "Kalol", "Kalol INA", "Kandla", "Kanjari", "Kanodar", "Kapadwanj", "Karachiya", "Karamsad", "Karjan", "Kathial", "Kathor", "Katpar", "Kavant", "Keshod", "Kevadiya", "Khambhaliya", "Khambhat", "Kharaghoda", "Khed Brahma", "Kheda", "Kheralu", "Kodinar", "Kosamba", "Kundla", "Kutch", "Kutiyana", "Lakhtar", "Lalpur", "Lambha", "Lathi", "Limbdi", "Limla", "Lunavada", "Madhapar", "Maflipur", "Mahemdavad", "Mahudha", "Mahuva", "Mahuvar", "Makarba", "Makarpura", "Makassar", "Maktampur", "Malia", "Malpur", "Manavadar", "Mandal", "Mandvi", "Mangrol", "Mansa", "Meghraj", "Mehsana", "Mendarla", "Mithapur", "Modasa", "Mogravadi", "Morbi", "Morvi", "Mundra", "Nadiad", "Naliya", "Nanakvada", "Nandej", "Nandesari", "Nandesari INA", "Naroda", "Navagadh", "Navagam Ghed", "Navsari", "Ode", "Okaf", "Okha", "Olpad", "Paddhari", "Padra", "Palanpur", "Palej", "Pali", "Palitana", "Paliyad", "Pandesara", "Panoli", "Pardi", "Parnera", "Parvat", "Patan", "Patdi", "Petlad", "Petrochemical Complex", "Porbandar", "Prantij", "Radhanpur", "Raiya", "Rajkot", "Rajpipla", "Rajula", "Ramod", "Ranavav", "Ranoli", "Rapar", "Sahij", "Salaya", "Sanand", "Sankheda", "Santrampur", "Saribujrang", "Sarigam INA", "Sayan", "Sayla", "Shahpur", "Shahwadi", "Shapar", "Shivrajpur", "Siddhapur", "Sidhpur", "Sihor", "Sika", "Singarva", "Sinor", "Sojitra", "Sola", "Songadh", "Suraj Karadi", "Surat", "Surendranagar", "Talaja", "Talala", "Talod", "Tankara", "Tarsali", "Thangadh", "Tharad", "Thasra", "Udyognagar", "Ukai", "Umbergaon", "Umbergaon INA", "Umrala", "Umreth", "Un", "Una", "Unjha", "Upleta", "Utran", "Uttarsanda", "V.U. Nagar", "V.V. Nagar", "Vadia", "Vadla", "Vadnagar", "Vadodara", "Vaghodia INA", "Valbhipur", "Vallabh Vidyanagar", "Valsad", "Valsad INA", "Vanthali", "Vapi", "Vapi INA", "Vartej", "Vasad", "Vasna Borsad INA", "Vaso", "Veraval", "Vidyanagar", "Vijalpor", "Vijapur", "Vinchhiya", "Vinzol", "Virpur", "Visavadar", "Visnagar", "Vyara", "Wadhwan", "Waghai", "Waghodia", "Wankaner", "Zalod", "Ambala", "Ambala Cantt", "Asan Khurd", "Asandh", "Ateli", "Babiyal", "Bahadurgarh", "Ballabgarh", "Barwala", "Bawal", "Bawani Khera", "Beri", "Bhiwani", "Bilaspur", "Buria", "Charkhi Dadri", "Chhachhrauli", "Chita", "Dabwali", "Dharuhera", "Dundahera", "Ellenabad", "Farakhpur", "Faridabad", "Farrukhnagar", "Fatehabad", "Firozpur Jhirka", "Gannaur", "Ghraunda", "Gohana", "Gurgaon", "Haileymandi", "Hansi", "Hasanpur", "Hathin", "Hisar", "Hissar", "Hodal", "Indri", "Jagadhri", "Jakhal Mandi", "Jhajjar", "Jind", "Julana", "Kaithal", "Kalanur", "Kalanwali", "Kalayat", "Kalka", "Kanina", "Kansepur", "Kardhan", "Karnal", "Kharkhoda", "Kheri Sampla", "Kundli", "Kurukshetra", "Ladrawan", "Ladwa", "Loharu", "Maham", "Mahendragarh", "Mustafabad", "Nagai Chaudhry", "Narayangarh", "Narnaul", "Narnaund", "Narwana", "Nilokheri", "Nuh", "Palwal", "Panchkula", "Panipat", "Panipat Taraf Ansar", "Panipat Taraf Makhdum Zadgan", "Panipat Taraf Rajputan", "Pehowa", "Pinjaur", "Punahana", "Pundri", "Radaur", "Raipur Rani", "Rania", "Ratiya", "Rewari", "Rohtak", "Ropar", "Sadauri", "Safidon", "Samalkha", "Sankhol", "Sasauli", "Shahabad", "Sirsa", "Siwani", "Sohna", "Sonipat", "Sukhrali", "Taoru", "Taraori", "Tauru", "Thanesar", "Tilpat", "Tohana", "Tosham", "Uchana", "Uklana Mandi", "Uncha Siwana", "Yamunanagar", "Arki", "Baddi", "Bakloh", "Banjar", "Bhota", "Bhuntar", "Bilaspur", "Chamba", "Chaupal", "Chuari Khas", "Dagshai", "Dalhousie", "Dalhousie Cantonment", "Damtal", "Daulatpur", "Dera Gopipur", "Dhalli", "Dharamshala", "Gagret", "Ghamarwin", "Hamirpur", "Jawala Mukhi", "Jogindarnagar", "Jubbal", "Jutogh", "Kala Amb", "Kalpa", "Kangra", "Kasauli", "Kot Khai", "Kullu", "Kulu", "Manali", "Mandi", "Mant Khas", "Mehatpur Basdehra", "Nadaun", "Nagrota", "Nahan", "Naina Devi", "Nalagarh", "Narkanda", "Nurpur", "Palampur", "Pandoh", "Paonta Sahib", "Parwanoo", "Parwanu", "Rajgarh", "Rampur", "Rawalsar", "Rohru", "Sabathu", "Santokhgarh", "Sarahan", "Sarka Ghat", "Seoni", "Shimla", "Sirmaur", "Solan", "Solon", "Sundarnagar", "Sundernagar", "Talai", "Theog", "Tira Sujanpur", "Una", "Yol", "Achabal", "Akhnur", "Anantnag", "Arnia", "Awantipora", "Badami Bagh", "Bandipur", "Banihal", "Baramula", "Baramulla", "Bari Brahmana", "Bashohli", "Batote", "Bhaderwah", "Bijbiara", "Billawar", "Birwah", "Bishna", "Budgam", "Charari Sharief", "Chenani", "Doda", "Duru-Verinag", "Gandarbat", "Gho Manhasan", "Gorah Salathian", "Gulmarg", "Hajan", "Handwara", "Hiranagar", "Jammu", "Jammu Cantonment", "Jammu Tawi", "Jourian", "Kargil", "Kathua", "Katra", "Khan Sahib", "Khour", "Khrew", "Kishtwar", "Kud", "Kukernag", "Kulgam", "Kunzer", "Kupwara", "Lakhenpur", "Leh", "Magam", "Mattan", "Naushehra", "Pahalgam", "Pampore", "Parole", "Pattan", "Pulwama", "Punch", "Qazigund", "Rajauri", "Ramban", "Ramgarh", "Ramnagar", "Ranbirsingh Pora", "Reasi", "Rehambal", "Samba", "Shupiyan", "Sopur", "Srinagar", "Sumbal", "Sunderbani", "Talwara", "Thanamandi", "Tral", "Udhampur", "Uri", "Vijaypur", "Adityapur", "Amlabad", "Angarpathar", "Ara", "Babua Kalan", "Bagbahra", "Baliapur", "Baliari", "Balkundra", "Bandhgora", "Barajamda", "Barhi", "Barka Kana", "Barki Saraiya", "Barughutu", "Barwadih", "Basaria", "Basukinath", "Bermo", "Bhagatdih", "Bhaurah", "Bhojudih", "Bhuli", "Bokaro", "Borio Bazar", "Bundu", "Chaibasa", "Chaitudih", "Chakradharpur", "Chakulia", "Chandaur", "Chandil", "Chandrapura", "Chas", "Chatra", "Chhatatanr", "Chhotaputki", "Chiria", "Chirkunda", "Churi", "Daltenganj", "Danguwapasi", "Dari", "Deoghar", "Deorikalan", "Devghar", "Dhanbad", "Dhanwar", "Dhaunsar", "Dugda", "Dumarkunda", "Dumka", "Egarkunr", "Gadhra", "Garwa", "Ghatsila", "Ghorabandha", "Gidi", "Giridih", "Gobindpur", "Godda", "Godhar", "Golphalbari", "Gomoh", "Gua", "Gumia", "Gumla", "Haludbani", "Hazaribag", "Hesla", "Husainabad", "Isri", "Jadugora", "Jagannathpur", "Jamadoba", "Jamshedpur", "Jamtara", "Jarangdih", "Jaridih", "Jasidih", "Jena", "Jharia", "Jharia Khas", "Jhinkpani", "Jhumri Tilaiya", "Jorapokhar", "Jugsalai", "Kailudih", "Kalikapur", "Kandra", "Kanke", "Katras", "Kedla", "Kenduadih", "Kharkhari", "Kharsawan", "Khelari", "Khunti", "Kiri Buru", "Kiriburu", "Kodarma", "Kuju", "Kurpania", "Kustai", "Lakarka", "Lapanga", "Latehar", "Lohardaga", "Loiya", "Loyabad", "Madhupur", "Mahesh Mundi", "Maithon", "Malkera", "Mango", "Manoharpur", "Marma", "Meghahatuburu Forest village", "Mera", "Meru", "Mihijam", "Mugma", "Muri", "Mushabani", "Nagri Kalan", "Netarhat", "Nirsa", "Noamundi", "Okni", "Orla", "Pakaur", "Palamau", "Palawa", "Panchet", "Panrra", "Paratdih", "Pathardih", "Patratu", "Phusro", "Pondar Kanali", "Rajmahal", "Ramgarh", "Ranchi", "Ray", "Rehla", "Religara", "Rohraband", "Sahibganj", "Sahnidih", "Saraidhela", "Saraikela", "Sarjamda", "Saunda", "Sewai", "Sijhua", "Sijua", "Simdega", "Sindari", "Sinduria", "Sini", "Sirka", "Siuliban", "Surubera", "Tati", "Tenudam", "Tisra", "Topa", "Topchanchi", "Adityanagar", "Adityapatna", "Afzalpur", "Ajjampur", "Aland", "Almatti Sitimani", "Alnavar", "Alur", "Ambikanagara", "Anekal", "Ankola", "Annigeri", "Arkalgud", "Arsikere", "Athni", "Aurad", "Badagavettu", "Badami", "Bagalkot", "Bagepalli", "Bailhongal", "Baindur", "Bajala", "Bajpe", "Banavar", "Bangarapet", "Bankapura", "Bannur", "Bantwal", "Basavakalyan", "Basavana Bagevadi", "Belagula", "Belakavadiq", "Belgaum", "Belgaum Cantonment", "Bellary", "Belluru", "Beltangadi", "Belur", "Belvata", "Bengaluru", "Bhadravati", "Bhalki", "Bhatkal", "Bhimarayanagudi", "Bhogadi", "Bidar", "Bijapur", "Bilgi", "Birur", "Bommanahalli", "Bommasandra", "Byadgi", "Byatarayanapura", "Chakranagar Colony", "Challakere", "Chamrajnagar", "Chamundi Betta", "Channagiri", "Channapatna", "Channarayapatna", "Chickballapur", "Chik Ballapur", "Chikkaballapur", "Chikmagalur", "Chiknayakanhalli", "Chikodi", "Chincholi", "Chintamani", "Chitaguppa", "Chitapur", "Chitradurga", "Coorg", "Dandeli", "Dargajogihalli", "Dasarahalli", "Davangere", "Devadurga", "Devagiri", "Devanhalli", "Dharwar", "Dhupdal", "Dod Ballapur", "Donimalai", "Gadag", "Gajendragarh", "Ganeshgudi", "Gangawati", "Gangoli", "Gauribidanur", "Gokak", "Gokak Falls", "Gonikoppal", "Gorur", "Gottikere", "Gubbi", "Gudibanda", "Gulbarga", "Guledgudda", "Gundlupet", "Gurmatkal", "Haliyal", "Hangal", "Harihar", "Harpanahalli", "Hassan", "Hatti", "Hatti Gold Mines", "Haveri", "Hebbagodi", "Hebbalu", "Hebri", "Heggadadevanakote", "Herohalli", "Hidkal", "Hindalgi", "Hirekerur", "Hiriyur", "Holalkere", "Hole Narsipur", "Homnabad", "Honavar", "Honnali", "Hosakote", "Hosanagara", "Hosangadi", "Hosdurga", "Hoskote", "Hospet", "Hubli", "Hukeri", "Hunasagi", "Hunasamaranahalli", "Hungund", "Hunsur", "Huvina Hadagalli", "Ilkal", "Indi", "Jagalur", "Jamkhandi", "Jevargi", "Jog Falls", "Kabini Colony", "Kadur", "Kalghatgi", "Kamalapuram", "Kampli", "Kanakapura", "Kangrali BK", "Kangrali KH", "Kannur", "Karkala", "Karwar", "Kemminja", "Kengeri", "Kerur", "Khanapur", "Kodigenahalli", "Kodiyal", "Kodlipet", "Kolar", "Kollegal", "Konanakunte", "Konanur", "Konnur", "Koppa", "Koppal", "Koratagere", "Kotekara", "Kothnur", "Kotturu", "Krishnapura", "Krishnarajanagar", "Krishnarajapura", "Krishnarajasagara", "Krishnarajpet", "Kudchi", "Kudligi", "Kudremukh", "Kumsi", "Kumta", "Kundapura", "Kundgol", "Kunigal", "Kurgunta", "Kushalnagar", "Kushtagi", "Kyathanahalli", "Lakshmeshwar", "Lingsugur", "Londa", "Maddur", "Madhugiri", "Madikeri", "Magadi", "Magod Falls", "Mahadeswara Hills", "Mahadevapura", "Mahalingpur", "Maisuru", "Maisuru Cantonment", "Malavalli", "Mallar", "Malpe", "Malur", "Manchenahalli", "Mandya", "Mangalore", "Mangaluru", "Manipal", "Manvi", "Maski", "Mastikatte Colony", "Mayakonda", "Melukote", "Molakalmuru", "Mudalgi", "Mudbidri", "Muddebihal", "Mudgal", "Mudhol", "Mudigere", "Mudushedde", "Mulbagal", "Mulgund", "Mulki", "Mulur", "Mundargi", "Mundgod", "Munirabad", "Munnur", "Murudeshwara", "Mysore", "Nagamangala", "Nanjangud", "Naragund", "Narasimharajapura", "Naravi", "Narayanpur", "Naregal", "Navalgund", "Nelmangala", "Nipani", "Nitte", "Nyamati", "Padu", "Pandavapura", "Pattanagere", "Pavagada", "Piriyapatna", "Ponnampet", "Puttur", "Rabkavi", "Raichur", "Ramanagaram", "Ramdurg", "Ranibennur", "Raybag", "Robertsonpet", "Ron", "Sadalgi", "Sagar", "Sakleshpur", "Saligram", "Sandur", "Sanivarsante", "Sankeshwar", "Sargur", "Sathyamangala", "Saundatti Yellamma", "Savanur", "Sedam", "Shahabad", "Shahabad A.C.C.", "Shahapur", "Shahpur", "Shaktinagar", "Shiggaon", "Shikarpur", "Shimoga", "Shirhatti", "Shorapur", "Shravanabelagola", "Shrirangapattana", "Siddapur", "Sidlaghatta", "Sindgi", "Sindhnur", "Sira", "Sirakoppa", "Sirsi", "Siruguppa", "Someshwar", "Somvarpet", "Sorab", "Sringeri", "Srinivaspur", "Sulya", "Suntikopa", "Talikota", "Tarikera", "Tekkalakota", "Terdal", "Thokur", "Thumbe", "Tiptur", "Tirthahalli", "Tirumakudal Narsipur", "Tonse", "Tumkur", "Turuvekere", "Udupi", "Ullal", "Uttarahalli", "Venkatapura", "Vijayapura", "Virarajendrapet", "Wadi", "Wadi A.C.C.", "Yadgir", "Yelahanka", "Yelandur", "Yelbarga", "Yellapur", "Yenagudde", "Adimaly", "Adoor", "Adur", "Akathiyur", "Alangad", "Alappuzha", "Aluva", "Ancharakandy", "Angamaly", "Aroor", "Arukutti", "Attingal", "Avinissery", "Azhikode North", "Azhikode South", "Azhiyur", "Balussery", "Bangramanjeshwar", "Beypur", "Brahmakulam", "Chala", "Chalakudi", "Changanacheri", "Chauwara", "Chavakkad", "Chelakkara", "Chelora", "Chendamangalam", "Chengamanad", "Chengannur", "Cheranallur", "Cheriyakadavu", "Cherthala", "Cherukunnu", "Cheruthazham", "Cheruvannur", "Cheruvattur", "Chevvur", "Chirakkal", "Chittur", "Chockli", "Churnikkara", "Dharmadam", "Edappal", "Edathala", "Elayavur", "Elur", "Eranholi", "Erattupetta", "Ernakulam", "Eruvatti", "Ettumanoor", "Feroke", "Guruvayur", "Haripad", "Hosabettu", "Idukki", "Iringaprom", "Irinjalakuda", "Iriveri", "Kadachira", "Kadalundi", "Kadamakkudy", "Kadirur", "Kadungallur", "Kakkodi", "Kalady", "Kalamassery", "Kalliasseri", "Kalpetta", "Kanhangad", "Kanhirode", "Kanjikkuzhi", "Kanjikode", "Kanjirappalli", "Kannadiparamba", "Kannangad", "Kannapuram", "Kannur", "Kannur Cantonment", "Karunagappally", "Karuvamyhuruthy", "Kasaragod", "Kasargod", "Kattappana", "Kayamkulam", "Kedamangalam", "Kochi", "Kodamthuruthu", "Kodungallur", "Koduvally", "Koduvayur", "Kokkothamangalam", "Kolazhy", "Kollam", "Komalapuram", "Koothattukulam", "Koratty", "Kothamangalam", "Kottarakkara", "Kottayam", "Kottayam Malabar", "Kottuvally", "Koyilandi", "Kozhikode", "Kudappanakunnu", "Kudlu", "Kumarakom", "Kumily", "Kunnamangalam", "Kunnamkulam", "Kurikkad", "Kurkkanchery", "Kuthuparamba", "Kuttakulam", "Kuttikkattur", "Kuttur", "Malappuram", "Mallappally", "Manjeri", "Manjeshwar", "Mannancherry", "Mannar", "Mannarakkat", "Maradu", "Marathakkara", "Marutharod", "Mattannur", "Mavelikara", "Mavilayi", "Mavur", "Methala", "Muhamma", "Mulavukad", "Mundakayam", "Munderi", "Munnar", "Muthakunnam", "Muvattupuzha", "Muzhappilangad", "Nadapuram", "Nadathara", "Narath", "Nattakam", "Nedumangad", "Nenmenikkara", "New Mahe", "Neyyattinkara", "Nileshwar", "Olavanna", "Ottapalam", "Ottappalam", "Paduvilayi", "Palai", "Palakkad", "Palayad", "Palissery", "Pallikkunnu", "Paluvai", "Panniyannur", "Pantalam", "Panthiramkavu", "Panur", "Pappinisseri", "Parassala", "Paravur", "Pathanamthitta", "Pathanapuram", "Pathiriyad", "Pattambi", "Pattiom", "Pavaratty", "Payyannur", "Peermade", "Perakam", "Peralasseri", "Peringathur", "Perinthalmanna", "Perole", "Perumanna", "Perumbaikadu", "Perumbavoor", "Pinarayi", "Piravam", "Ponnani", "Pottore", "Pudukad", "Punalur", "Puranattukara", "Puthunagaram", "Puthuppariyaram", "Puzhathi", "Ramanattukara", "Shoranur", "Sultans Battery", "Sulthan Bathery", "Talipparamba", "Thaikkad", "Thalassery", "Thannirmukkam", "Theyyalingal", "Thiruvalla", "Thiruvananthapuram", "Thiruvankulam", "Thodupuzha", "Thottada", "Thrippunithura", "Thrissur", "Tirur", "Udma", "Vadakara", "Vaikam", "Valapattam", "Vallachira", "Varam", "Varappuzha", "Varkala", "Vayalar", "Vazhakkala", "Venmanad", "Villiappally", "Wayanad", "Agethi", "Amini", "Androth Island", "Kavaratti", "Minicoy", "Agar", "Ajaigarh", "Akoda", "Akodia", "Alampur", "Alirajpur", "Alot", "Amanganj", "Amarkantak", "Amarpatan", "Amarwara", "Ambada", "Ambah", "Amla", "Amlai", "Anjad", "Antri", "Anuppur", "Aron", "Ashoknagar", "Ashta", "Babai", "Bada Malhera", "Badagaon", "Badagoan", "Badarwas", "Badawada", "Badi", "Badkuhi", "Badnagar", "Badnawar", "Badod", "Badoda", "Badra", "Bagh", "Bagli", "Baihar", "Baikunthpur", "Bakswaha", "Balaghat", "Baldeogarh", "Bamaniya", "Bamhani", "Bamor", "Bamora", "Banda", "Bangawan", "Bansatar Kheda", "Baraily", "Barela", "Barghat", "Bargi", "Barhi", "Barigarh", "Barwaha", "Barwani", "Basoda", "Begamganj", "Beohari", "Berasia", "Betma", "Betul", "Betul Bazar", "Bhainsdehi", "Bhamodi", "Bhander", "Bhanpura", "Bharveli", "Bhaurasa", "Bhavra", "Bhedaghat", "Bhikangaon", "Bhilakhedi", "Bhind", "Bhitarwar", "Bhopal", "Bhuibandh", "Biaora", "Bijawar", "Bijeypur", "Bijrauni", "Bijuri", "Bilaua", "Bilpura", "Bina Railway Colony", "Bina-Etawa", "Birsinghpur", "Boda", "Budhni", "Burhanpur", "Burhar", "Chachaura Binaganj", "Chakghat", "Chandameta Butar", "Chanderi", "Chandia", "Chandla", "Chaurai Khas", "Chhatarpur", "Chhindwara", "Chhota Chhindwara", "Chichli", "Chitrakut", "Churhat", "Daboh", "Dabra", "Damoh", "Damua", "Datia", "Deodara", "Deori", "Deori Khas", "Depalpur", "Devendranagar", "Devhara", "Dewas", "Dhamnod", "Dhana", "Dhanpuri", "Dhar", "Dharampuri", "Dighawani", "Diken", "Dindori", "Dola", "Dumar Kachhar", "Dungariya Chhapara", "Gadarwara", "Gairatganj", "Gandhi Sagar Hydel Colony", "Ganjbasoda", "Garhakota", "Garhi Malhara", "Garoth", "Gautapura", "Ghansor", "Ghuwara", "Gogaon", "Gogapur", "Gohad", "Gormi", "Govindgarh", "Guna", "Gurh", "Gwalior", "Hanumana", "Harda", "Harpalpur", "Harrai", "Harsud", "Hatod", "Hatpipalya", "Hatta", "Hindoria", "Hirapur", "Hoshangabad", "Ichhawar", "Iklehra", "Indergarh", "Indore", "Isagarh", "Itarsi", "Jabalpur", "Jabalpur Cantonment", "Jabalpur G.C.F", "Jaisinghnagar", "Jaithari", "Jaitwara", "Jamai", "Jaora", "Jatachhapar", "Jatara", "Jawad", "Jawar", "Jeronkhalsa", "Jhabua", "Jhundpura", "Jiran", "Jirapur", "Jobat", "Joura", "Kailaras", "Kaimur", "Kakarhati", "Kalichhapar", "Kanad", "Kannod", "Kantaphod", "Kareli", "Karera", "Kari", "Karnawad", "Karrapur", "Kasrawad", "Katangi", "Katni", "Kelhauri", "Khachrod", "Khajuraho", "Khamaria", "Khand", "Khandwa", "Khaniyadhana", "Khargapur", "Khargone", "Khategaon", "Khetia", "Khilchipur", "Khirkiya", "Khujner", "Khurai", "Kolaras", "Kotar", "Kothi", "Kotma", "Kukshi", "Kumbhraj", "Kurwai", "Lahar", "Lakhnadon", "Lateri", "Laundi", "Lidhora Khas", "Lodhikheda", "Loharda", "Machalpur", "Madhogarh", "Maharajpur", "Maheshwar", "Mahidpur", "Maihar", "Majholi", "Makronia", "Maksi", "Malaj Khand", "Malanpur", "Malhargarh", "Manasa", "Manawar", "Mandav", "Mandideep", "Mandla", "Mandleshwar", "Mandsaur", "Manegaon", "Mangawan", "Manglaya Sadak", "Manpur", "Mau", "Mauganj", "Meghnagar", "Mehara Gaon", "Mehgaon", "Mhaugaon", "Mhow", "Mihona", "Mohgaon", "Morar", "Morena", "Morwa", "Multai", "Mundi", "Mungaoli", "Murwara", "Nagda", "Nagod", "Nagri", "Naigarhi", "Nainpur", "Nalkheda", "Namli", "Narayangarh", "Narsimhapur", "Narsingarh", "Narsinghpur", "Narwar", "Nasrullaganj", "Naudhia", "Naugaon", "Naurozabad", "Neemuch", "Nepa Nagar", "Neuton Chikhli Kalan", "Nimach", "Niwari", "Obedullaganj", "Omkareshwar", "Orachha", "Ordinance Factory Itarsi", "Pachmarhi", "Pachmarhi Cantonment", "Pachore", "Palchorai", "Palda", "Palera", "Pali", "Panagar", "Panara", "Pandaria", "Pandhana", "Pandhurna", "Panna", "Pansemal", "Parasia", "Pasan", "Patan", "Patharia", "Pawai", "Petlawad", "Phuph Kalan", "Pichhore", "Pipariya", "Pipliya Mandi", "Piploda", "Pithampur", "Polay Kalan", "Porsa", "Prithvipur", "Raghogarh", "Rahatgarh", "Raisen", "Rajakhedi", "Rajgarh", "Rajnagar", "Rajpur", "Rampur Baghelan", "Rampur Naikin", "Rampura", "Ranapur", "Ranipura", "Ratangarh", "Ratlam", "Ratlam Kasba", "Rau", "Rehli", "Rehti", "Rewa", "Sabalgarh", "Sagar", "Sagar Cantonment", "Sailana", "Sanawad", "Sanchi", "Sanwer", "Sarangpur", "Sardarpur", "Sarni", "Satai", "Satna", "Satwas", "Sausar", "Sehore", "Semaria", "Sendhwa", "Seondha", "Seoni", "Seoni Malwa", "Sethia", "Shahdol", "Shahgarh", "Shahpur", "Shahpura", "Shajapur", "Shamgarh", "Sheopur", "Shivpuri", "Shujalpur", "Sidhi", "Sihora", "Singolo", "Singrauli", "Sinhasa", "Sirgora", "Sirmaur", "Sironj", "Sitamau", "Sohagpur", "Sonkatch", "Soyatkalan", "Suhagi", "Sultanpur", "Susner", "Suthaliya", "Tal", "Talen", "Tarana", "Taricharkalan", "Tekanpur", "Tendukheda", "Teonthar", "Thandia", "Tikamgarh", "Timarni", "Tirodi", "Udaipura", "Ujjain", "Ukwa", "Umaria", "Unchahara", "Unhel", "Vehicle Factory Jabalpur", "Vidisha", "Vijayraghavgarh", "Waraseoni", "Achalpur", "Aheri", "Ahmadnagar Cantonment", "Ahmadpur", "Ahmednagar", "Ajra", "Akalkot", "Akkalkuwa", "Akola", "Akot", "Alandi", "Alibag", "Allapalli", "Alore", "Amalner", "Ambad", "Ambajogai", "Ambernath", "Ambivali Tarf Wankhal", "Amgaon", "Amravati", "Anjangaon", "Arvi", "Ashta", "Ashti", "Aurangabad", "Aurangabad Cantonment", "Ausa", "Babhulgaon", "Badlapur", "Balapur", "Ballarpur", "Baramati", "Barshi", "Basmat", "Beed", "Bhadravati", "Bhagur", "Bhandara", "Bhigvan", "Bhingar", "Bhiwandi", "Bhokhardan", "Bhor", "Bhosari", "Bhum", "Bhusawal", "Bid", "Biloli", "Birwadi", "Boisar", "Bop Khel", "Brahmapuri", "Budhgaon", "Buldana", "Buldhana", "Butibori", "Chakan", "Chalisgaon", "Chandrapur", "Chandur", "Chandur Bazar", "Chandvad", "Chicholi", "Chikhala", "Chikhaldara", "Chikhli", "Chinchani", "Chinchwad", "Chiplun", "Chopda", "Dabhol", "Dahance", "Dahanu", "Daharu", "Dapoli Camp", "Darwa", "Daryapur", "Dattapur", "Daund", "Davlameti", "Deglur", "Dehu Road", "Deolali", "Deolali Pravara", "Deoli", "Desaiganj", "Deulgaon Raja", "Dewhadi", "Dharangaon", "Dharmabad", "Dharur", "Dhatau", "Dhule", "Digdoh", "Diglur", "Digras", "Dombivli", "Dondaicha", "Dudhani", "Durgapur", "Dyane", "Edandol", "Eklahare", "Faizpur", "Fekari", "Gadchiroli", "Gadhinghaj", "Gandhi Nagar", "Ganeshpur", "Gangakher", "Gangapur", "Gevrai", "Ghatanji", "Ghoti", "Ghugus", "Ghulewadi", "Godoli", "Gondia", "Guhagar", "Hadgaon", "Harnai Beach", "Hinganghat", "Hingoli", "Hupari", "Ichalkaranji", "Igatpuri", "Indapur", "Jaisinghpur", "Jalgaon", "Jalna", "Jamkhed", "Jawhar", "Jaysingpur", "Jejuri", "Jintur", "Junnar", "Kabnur", "Kagal", "Kalamb", "Kalamnuri", "Kalas", "Kalmeshwar", "Kalundre", "Kalyan", "Kamthi", "Kamthi Cantonment", "Kandari", "Kandhar", "Kandri", "Kandri II", "Kanhan", "Kankavli", "Kannad", "Karad", "Karanja", "Karanje Tarf", "Karivali", "Karjat", "Karmala", "Kasara Budruk", "Katai", "Katkar", "Katol", "Kegaon", "Khadkale", "Khadki", "Khamgaon", "Khapa", "Kharadi", "Kharakvasla", "Khed", "Kherdi", "Khoni", "Khopoli", "Khuldabad", "Kinwat", "Kodoli", "Kolhapur", "Kon", "Kondumal", "Kopargaon", "Kopharad", "Koradi", "Koregaon", "Korochi", "Kudal", "Kundaim", "Kundalwadi", "Kurandvad", "Kurduvadi", "Kusgaon Budruk", "Lanja", "Lasalgaon", "Latur", "Loha", "Lohegaon", "Lonar", "Lonavala", "Madhavnagar", "Mahabaleshwar", "Mahad", "Mahadula", "Maindargi", "Majalgaon", "Malegaon", "Malgaon", "Malkapur", "Malwan", "Manadur", "Manchar", "Mangalvedhe", "Mangrul Pir", "Manmad", "Manor", "Mansar", "Manwath", "Mapuca", "Matheran", "Mehkar", "Mhasla", "Mhaswad", "Mira Bhayandar", "Miraj", "Mohpa", "Mohpada", "Moram", "Morshi", "Mowad", "Mudkhed", "Mukhed", "Mul", "Mulshi", "Mumbai", "Murbad", "Murgud", "Murtijapur", "Murud", "Nachane", "Nagardeole", "Nagothane", "Nagpur", "Nakoda", "Nalasopara", "Naldurg", "Nanded", "Nandgaon", "Nandura", "Nandurbar", "Narkhed", "Nashik", "Navapur", "Navi Mumbai", "Navi Mumbai Panvel", "Neral", "Nigdi", "Nilanga", "Nildoh", "Nimbhore", "Ojhar", "Osmanabad", "Pachgaon", "Pachora", "Padagha", "Paithan", "Palghar", "Pali", "Panchgani", "Pandhakarwada", "Pandharpur", "Panhala", "Panvel", "Paranda", "Parbhani", "Parli", "Parola", "Partur", "Pasthal", "Patan", "Pathardi", "Pathri", "Patur", "Pawni", "Pen", "Pethumri", "Phaltan", "Pimpri", "Poladpur", "Pulgaon", "Pune", "Pune Cantonment", "Purna", "Purushottamnagar", "Pusad", "Rahimatpur", "Rahta Pimplas", "Rahuri", "Raigad", "Rajapur", "Rajgurunagar", "Rajur", "Rajura", "Ramtek", "Ratnagiri", "Ravalgaon", "Raver", "Revadanda", "Risod", "Roha Ashtami", "Sakri", "Sandor", "Sangamner", "Sangli", "Sangole", "Sasti", "Sasvad", "Satana", "Satara", "Savantvadi", "Savda", "Savner", "Sawari Jawharnagar", "Selu", "Shahada", "Shahapur", "Shegaon", "Shelar", "Shendurjana", "Shirdi", "Shirgaon", "Shirpur", "Shirur", "Shirwal", "Shivatkar", "Shrigonda", "Shrirampur", "Shrirampur Rural", "Sillewada", "Sillod", "Sindhudurg", "Sindi", "Sindi Turf Hindnagar", "Sindkhed Raja", "Singnapur", "Sinnar", "Sirur", "Sitasawangi", "Solapur", "Sonai", "Sonegaon", "Soyagaon", "Srivardhan", "Surgana", "Talegaon Dabhade", "Taloda", "Taloja", "Talwade", "Tarapur", "Tasgaon", "Tathavade", "Tekadi", "Telhara", "Thane", "Tirira", "Totaladoh", "Trimbak", "Tuljapur", "Tumsar", "Uchgaon", "Udgir", "Ulhasnagar", "Umarga", "Umarkhed", "Umarsara", "Umbar Pada Nandade", "Umred", "Umri Pragane Balapur", "Uran", "Uran Islampur", "Utekhol", "Vada", "Vadgaon", "Vadgaon Kasba", "Vaijapur", "Vanvadi", "Varangaon", "Vasai", "Vasantnagar", "Vashind", "Vengurla", "Virar", "Visapur", "Vite", "Vithalwadi", "Wadi", "Waghapur", "Wai", "Wajegaon", "Walani", "Wanadongri", "Wani", "Wardha", "Warora", "Warthi", "Warud", "Washim", "Yaval", "Yavatmal", "Yeola", "Yerkheda", "Andro", "Bijoy Govinda", "Bishnupur", "Churachandpur", "Heriok", "Imphal", "Jiribam", "Kakching", "Kakching Khunou", "Khongman", "Kumbi", "Kwakta", "Lamai", "Lamjaotongba", "Lamshang", "Lilong", "Mayang Imphal", "Moirang", "Moreh", "Nambol", "Naoriya Pakhanglakpa", "Ningthoukhong", "Oinam", "Porompat", "Samurou", "Sekmai Bazar", "Senapati", "Sikhong Sekmai", "Sugnu", "Thongkhong Laxmi Bazar", "Thoubal", "Torban", "Wangjing", "Wangoi", "Yairipok", "Baghmara", "Cherrapunji", "Jawai", "Madanrting", "Mairang", "Mawlai", "Nongmynsong", "Nongpoh", "Nongstoin", "Nongthymmai", "Pynthorumkhrah", "Resubelpara", "Shillong", "Shillong Cantonment", "Tura", "Williamnagar", "Aizawl", "Bairabi", "Biate", "Champhai", "Darlawn", "Hnahthial", "Kawnpui", "Khawhai", "Khawzawl", "Kolasib", "Lengpui", "Lunglei", "Mamit", "North Vanlaiphai", "Saiha", "Sairang", "Saitul", "Serchhip", "Thenzawl", "Tlabung", "Vairengte", "Zawlnuam", "Chumukedima", "Dimapur", "Kohima", "Mokokchung", "Mon", "Phek", "Tuensang", "Wokha", "Zunheboto", "Anandapur", "Angul", "Aska", "Athgarh", "Athmallik", "Balagoda", "Balangir", "Balasore", "Baleshwar", "Balimeta", "Balugaon", "Banapur", "Bangura", "Banki", "Banposh", "Barbil", "Bargarh", "Baripada", "Barpali", "Basudebpur", "Baudh", "Belagachhia", "Belaguntha", "Belpahar", "Berhampur", "Bhadrak", "Bhanjanagar", "Bhawanipatna", "Bhuban", "Bhubaneswar", "Binika", "Birmitrapur", "Bishama Katek", "Bolangir", "Brahmapur", "Brajrajnagar", "Buguda", "Burla", "Byasanagar", "Champua", "Chandapur", "Chandbali", "Chandili", "Charibatia", "Chatrapur", "Chikitigarh", "Chitrakonda", "Choudwar", "Cuttack", "Dadhapatna", "Daitari", "Damanjodi", "Deogarh", "Deracolliery", "Dhamanagar", "Dhenkanal", "Digapahandi", "Dungamal", "Fertilizer Corporation of Indi", "Ganjam", "Ghantapada", "Gopalpur", "Gudari", "Gunupur", "Hatibandha", "Hinjilikatu", "Hirakud", "Jagatsinghapur", "Jajpur", "Jalda", "Jaleswar", "Jatni", "Jaypur", "Jeypore", "Jharsuguda", "Jhumpura", "Joda", "Junagarh", "Kamakhyanagar", "Kantabanji", "Kantilo", "Karanja", "Kashinagara", "Kataka", "Kavisuryanagar", "Kendrapara", "Kendujhar", "Keonjhar", "Kesinga", "Khaliapali", "Khalikote", "Khandaparha", "Kharhial", "Kharhial Road", "Khatiguda", "Khurda", "Kochinda", "Kodala", "Konark", "Koraput", "Kotaparh", "Lanjigarh", "Lattikata", "Makundapur", "Malkangiri", "Mukhiguda", "Nabarangpur", "Nalco", "Naurangapur", "Nayagarh", "Nilagiri", "Nimaparha", "Nuapada", "Nuapatna", "OCL Industrialship", "Padampur", "Paradip", "Paradwip", "Parlakimidi", "Patamundai", "Patnagarh", "Phulabani", "Pipili", "Polasara", "Pratapsasan", "Puri", "Purushottampur", "Rairangpur", "Raj Gangpur", "Rambha", "Raurkela", "Raurkela Civil Township", "Rayagada", "Redhakhol", "Remuna", "Rengali", "Rourkela", "Sambalpur", "Sinapali", "Sonepur", "Sorada", "Soro", "Sunabeda", "Sundargarh", "Talcher", "Talcher Thermal Power Station", "Tarabha", "Tensa", "Titlagarh", "Udala", "Udayagiri", "Umarkot", "Vikrampur", "Ariankuppam", "Karaikal", "Kurumbapet", "Mahe", "Ozhukarai", "Pondicherry", "Villianur", "Yanam", "Abohar", "Adampur", "Ahmedgarh", "Ajnala", "Akalgarh", "Alawalpur", "Amloh", "Amritsar", "Amritsar Cantonment", "Anandpur Sahib", "Badhni Kalan", "Bagh Purana", "Balachaur", "Banaur", "Banga", "Banur", "Baretta", "Bariwala", "Barnala", "Bassi Pathana", "Batala", "Bathinda", "Begowal", "Behrampur", "Bhabat", "Bhadur", "Bhankharpur", "Bharoli Kalan", "Bhawanigarh", "Bhikhi", "Bhikhiwind", "Bhisiana", "Bhogpur", "Bhuch", "Bhulath", "Budha Theh", "Budhlada", "Chima", "Chohal", "Dasuya", "Daulatpur", "Dera Baba Nanak", "Dera Bassi", "Dhanaula", "Dharam Kot", "Dhariwal", "Dhilwan", "Dhuri", "Dinanagar", "Dirba", "Doraha", "Faridkot", "Fateh Nangal", "Fatehgarh Churian", "Fatehgarh Sahib", "Fazilka", "Firozpur", "Firozpur Cantonment", "Gardhiwala", "Garhshankar", "Ghagga", "Ghanaur", "Giddarbaha", "Gobindgarh", "Goniana", "Goraya", "Gurdaspur", "Guru Har Sahai", "Hajipur", "Handiaya", "Hariana", "Hoshiarpur", "Hussainpur", "Jagraon", "Jaitu", "Jalalabad", "Jalandhar", "Jalandhar Cantonment", "Jandiala", "Jugial", "Kalanaur", "Kapurthala", "Karoran", "Kartarpur", "Khamanon", "Khanauri", "Khanna", "Kharar", "Khem Karan", "Kot Fatta", "Kot Isa Khan", "Kot Kapura", "Kotkapura", "Kurali", "Lalru", "Lehra Gaga", "Lodhian Khas", "Longowal", "Ludhiana", "Machhiwara", "Mahilpur", "Majitha", "Makhu", "Malaut", "Malerkotla", "Maloud", "Mandi Gobindgarh", "Mansa", "Maur", "Moga", "Mohali", "Moonak", "Morinda", "Mukerian", "Muktsar", "Mullanpur Dakha", "Mullanpur Garibdas", "Munak", "Muradpura", "Nabha", "Nakodar", "Nangal", "Nawashahr", "Naya Nangal", "Nehon", "Nurmahal", "Pathankot", "Patiala", "Patti", "Pattran", "Payal", "Phagwara", "Phillaur", "Qadian", "Rahon", "Raikot", "Raja Sansi", "Rajpura", "Ram Das", "Raman", "Rampura", "Rayya", "Rupnagar", "Rurki Kasba", "Sahnewal", "Samana", "Samrala", "Sanaur", "Sangat", "Sangrur", "Sansarpur", "Sardulgarh", "Shahkot", "Sham Churasi", "Shekhpura", "Sirhind", "Sri Hargobindpur", "Sujanpur", "Sultanpur Lodhi", "Sunam", "Talwandi Bhai", "Talwara", "Tappa", "Tarn Taran", "Urmar Tanda", "Zira", "Zirakpur", "Abu Road", "Ajmer", "Aklera", "Alwar", "Amet", "Antah", "Anupgarh", "Asind", "Bagar", "Bagru", "Bahror", "Bakani", "Bali", "Balotra", "Bandikui", "Banswara", "Baran", "Bari", "Bari Sadri", "Barmer", "Basi", "Basni Belima", "Baswa", "Bayana", "Beawar", "Begun", "Bhadasar", "Bhadra", "Bhalariya", "Bharatpur", "Bhasawar", "Bhawani Mandi", "Bhawri", "Bhilwara", "Bhindar", "Bhinmal", "Bhiwadi", "Bijoliya Kalan", "Bikaner", "Bilara", "Bissau", "Borkhera", "Budhpura", "Bundi", "Chatsu", "Chechat", "Chhabra", "Chhapar", "Chhipa Barod", "Chhoti Sadri", "Chirawa", "Chittaurgarh", "Chittorgarh", "Chomun", "Churu", "Daosa", "Dariba", "Dausa", "Deoli", "Deshnok", "Devgarh", "Devli", "Dhariawad", "Dhaulpur", "Dholpur", "Didwana", "Dig", "Dungargarh", "Dungarpur", "Falna", "Fatehnagar", "Fatehpur", "Gajsinghpur", "Galiakot", "Ganganagar", "Gangapur", "Goredi Chancha", "Gothra", "Govindgarh", "Gulabpura", "Hanumangarh", "Hindaun", "Indragarh", "Jahazpur", "Jaipur", "Jaisalmer", "Jaiselmer", "Jaitaran", "Jalore", "Jhalawar", "Jhalrapatan", "Jhunjhunun", "Jobner", "Jodhpur", "Kaithun", "Kaman", "Kankroli", "Kanor", "Kapasan", "Kaprain", "Karanpura", "Karauli", "Kekri", "Keshorai Patan", "Kesrisinghpur", "Khairthal", "Khandela", "Khanpur", "Kherli", "Kherliganj", "Kherwara Chhaoni", "Khetri", "Kiranipura", "Kishangarh", "Kishangarh Ranwal", "Kolvi Rajendrapura", "Kot Putli", "Kota", "Kuchaman", "Kuchera", "Kumbhalgarh", "Kumbhkot", "Kumher", "Kushalgarh", "Lachhmangarh", "Ladnun", "Lakheri", "Lalsot", "Losal", "Madanganj", "Mahu Kalan", "Mahwa", "Makrana", "Malpura", "Mandal", "Mandalgarh", "Mandawar", "Mandwa", "Mangrol", "Manohar Thana", "Manoharpur", "Marwar", "Merta", "Modak", "Mount Abu", "Mukandgarh", "Mundwa", "Nadbai", "Naenwa", "Nagar", "Nagaur", "Napasar", "Naraina", "Nasirabad", "Nathdwara", "Nawa", "Nawalgarh", "Neem Ka Thana", "Neemrana", "Newa Talai", "Nimaj", "Nimbahera", "Niwai", "Nohar", "Nokha", "One SGM", "Padampur", "Pali", "Partapur", "Parvatsar", "Pasoond", "Phalna", "Phalodi", "Phulera", "Pilani", "Pilibanga", "Pindwara", "Pipalia Kalan", "Pipar", "Pirawa", "Pokaran", "Pratapgarh", "Pushkar", "Raipur", "Raisinghnagar", "Rajakhera", "Rajaldesar", "Rajgarh", "Rajsamand", "Ramganj Mandi", "Ramgarh", "Rani", "Raniwara", "Ratan Nagar", "Ratangarh", "Rawatbhata", "Rawatsar", "Rikhabdev", "Ringas", "Sadri", "Sadulshahar", "Sagwara", "Salumbar", "Sambhar", "Samdari", "Sanchor", "Sangariya", "Sangod", "Sardarshahr", "Sarwar", "Satal Kheri", "Sawai Madhopur", "Sewan Kalan", "Shahpura", "Sheoganj", "Sikar", "Sirohi", "Siwana", "Sogariya", "Sojat", "Sojat Road", "Sri Madhopur", "Sriganganagar", "Sujangarh", "Suket", "Sumerpur", "Sunel", "Surajgarh", "Suratgarh", "Swaroopganj", "Takhatgarh", "Taranagar", "Three STR", "Tijara", "Toda Bhim", "Toda Raisingh", "Todra", "Tonk", "Udaipur", "Udpura", "Uniara", "Vanasthali", "Vidyavihar", "Vijainagar", "Viratnagar", "Wer", "Gangtok", "Gezing", "Jorethang", "Mangan", "Namchi", "Naya Bazar", "No City", "Rangpo", "Sikkim", "Singtam", "Upper Tadong", "Abiramam", "Achampudur", "Acharapakkam", "Acharipallam", "Achipatti", "Adikaratti", "Adiramapattinam", "Aduturai", "Adyar", "Agaram", "Agasthiswaram", "Akkaraipettai", "Alagappapuram", "Alagapuri", "Alampalayam", "Alandur", "Alanganallur", "Alangayam", "Alangudi", "Alangulam", "Alanthurai", "Alapakkam", "Allapuram", "Alur", "Alwar Tirunagari", "Alwarkurichi", "Ambasamudram", "Ambur", "Ammainaickanur", "Ammaparikuppam", "Ammapettai", "Ammavarikuppam", "Ammur", "Anaimalai", "Anaiyur", "Anakaputhur", "Ananthapuram", "Andanappettai", "Andipalayam", "Andippatti", "Anjugramam", "Annamalainagar", "Annavasal", "Annur", "Anthiyur", "Appakudal", "Arachalur", "Arakandanallur", "Arakonam", "Aralvaimozhi", "Arani", "Arani Road", "Arantangi", "Arasiramani", "Aravakurichi", "Aravankadu", "Arcot", "Arimalam", "Ariyalur", "Ariyappampalayam", "Ariyur", "Arni", "Arulmigu Thirumuruganpundi", "Arumanai", "Arumbavur", "Arumuganeri", "Aruppukkottai", "Ashokapuram", "Athani", "Athanur", "Athimarapatti", "Athipattu", "Athur", "Attayyampatti", "Attur", "Auroville", "Avadattur", "Avadi", "Avalpundurai", "Avaniapuram", "Avinashi", "Ayakudi", "Ayanadaippu", "Aygudi", "Ayothiapattinam", "Ayyalur", "Ayyampalayam", "Ayyampettai", "Azhagiapandiapuram", "Balakrishnampatti", "Balakrishnapuram", "Balapallam", "Balasamudram", "Bargur", "Belur", "Berhatty", "Bhavani", "Bhawanisagar", "Bhuvanagiri", "Bikketti", "Bodinayakkanur", "Brahmana Periya Agraharam", "Buthapandi", "Buthipuram", "Chatrapatti", "Chembarambakkam", "Chengalpattu", "Chengam", "Chennai", "Chennasamudram", "Chennimalai", "Cheranmadevi", "Cheruvanki", "Chetpet", "Chettiarpatti", "Chettipalaiyam", "Chettipalayam Cantonment", "Chettithangal", "Cheyur", "Cheyyar", "Chidambaram", "Chinalapatti", "Chinna Anuppanadi", "Chinna Salem", "Chinnakkampalayam", "Chinnammanur", "Chinnampalaiyam", "Chinnasekkadu", "Chinnavedampatti", "Chitlapakkam", "Chittodu", "Cholapuram", "Coimbatore", "Coonoor", "Courtalam", "Cuddalore", "Dalavaipatti", "Darasuram", "Denkanikottai", "Desur", "Devadanapatti", "Devakkottai", "Devakottai", "Devanangurichi", "Devarshola", "Devasthanam", "Dhalavoipuram", "Dhali", "Dhaliyur", "Dharapadavedu", "Dharapuram", "Dharmapuri", "Dindigul", "Dusi", "Edaganasalai", "Edaikodu", "Edakalinadu", "Elathur", "Elayirampannai", "Elumalai", "Eral", "Eraniel", "Eriodu", "Erode", "Erumaipatti", "Eruvadi", "Ethapur", "Ettaiyapuram", "Ettimadai", "Ezhudesam", "Ganapathipuram", "Gandhi Nagar", "Gangaikondan", "Gangavalli", "Ganguvarpatti", "Gingi", "Gopalasamudram", "Gopichettipalaiyam", "Gudalur", "Gudiyattam", "Guduvanchery", "Gummidipoondi", "Hanumanthampatti", "Harur", "Harveypatti", "Highways", "Hosur", "Hubbathala", "Huligal", "Idappadi", "Idikarai", "Ilampillai", "Ilanji", "Iluppaiyurani", "Iluppur", "Inam Karur", "Injambakkam", "Irugur", "Jaffrabad", "Jagathala", "Jalakandapuram", "Jalladiampet", "Jambai", "Jayankondam", "Jolarpet", "Kadambur", "Kadathur", "Kadayal", "Kadayampatti", "Kadayanallur", "Kadiapatti", "Kalakkad", "Kalambur", "Kalapatti", "Kalappanaickenpatti", "Kalavai", "Kalinjur", "Kaliyakkavilai", "Kallakkurichi", "Kallakudi", "Kallidaikurichchi", "Kallukuttam", "Kallupatti", "Kalpakkam", "Kalugumalai", "Kamayagoundanpatti", "Kambainallur", "Kambam", "Kamuthi", "Kanadukathan", "Kanakkampalayam", "Kanam", "Kanchipuram", "Kandanur", "Kangayam", "Kangayampalayam", "Kangeyanallur", "Kaniyur", "Kanjikoil", "Kannadendal", "Kannamangalam", "Kannampalayam", "Kannankurichi", "Kannapalaiyam", "Kannivadi", "Kanyakumari", "Kappiyarai", "Karaikkudi", "Karamadai", "Karambakkam", "Karambakkudi", "Kariamangalam", "Kariapatti", "Karugampattur", "Karumandi Chellipalayam", "Karumathampatti", "Karumbakkam", "Karungal", "Karunguzhi", "Karuppur", "Karur", "Kasipalaiyam", "Kasipalayam G", "Kathirvedu", "Kathujuganapalli", "Katpadi", "Kattivakkam", "Kattumannarkoil", "Kattupakkam", "Kattuputhur", "Kaveripakkam", "Kaveripattinam", "Kavundampalaiyam", "Kavundampalayam", "Kayalpattinam", "Kayattar", "Kelamangalam", "Kelambakkam", "Kembainaickenpalayam", "Kethi", "Kilakarai", "Kilampadi", "Kilkulam", "Kilkunda", "Killiyur", "Killlai", "Kilpennathur", "Kilvelur", "Kinathukadavu", "Kiramangalam", "Kiranur", "Kiripatti", "Kizhapavur", "Kmarasamipatti", "Kochadai", "Kodaikanal", "Kodambakkam", "Kodavasal", "Kodumudi", "Kolachal", "Kolappalur", "Kolathupalayam", "Kolathur", "Kollankodu", "Kollankoil", "Komaralingam", "Komarapalayam", "Kombai", "Konakkarai", "Konavattam", "Kondalampatti", "Konganapuram", "Koradacheri", "Korampallam", "Kotagiri", "Kothinallur", "Kottaiyur", "Kottakuppam", "Kottaram", "Kottivakkam", "Kottur", "Kovilpatti", "Koyampattur", "Krishnagiri", "Krishnarayapuram", "Krishnasamudram", "Kuchanur", "Kuhalur", "Kulasekarappattinam", "Kulasekarapuram", "Kulithalai", "Kumarapalaiyam", "Kumarapalayam", "Kumarapuram", "Kumbakonam", "Kundrathur", "Kuniyamuthur", "Kunnathur", "Kunur", "Kuraikundu", "Kurichi", "Kurinjippadi", "Kurudampalaiyam", "Kurumbalur", "Kuthalam", "Kuthappar", "Kuttalam", "Kuttanallur", "Kuzhithurai", "Labbaikudikadu", "Lakkampatti", "Lalgudi", "Lalpet", "Llayangudi", "Madambakkam", "Madanur", "Madathukulam", "Madhavaram", "Madippakkam", "Madukkarai", "Madukkur", "Madurai", "Maduranthakam", "Maduravoyal", "Mahabalipuram", "Makkinanpatti", "Mallamuppampatti", "Mallankinaru", "Mallapuram", "Mallasamudram", "Mallur", "Mamallapuram", "Mamsapuram", "Manachanallur", "Manali", "Manalmedu", "Manalurpet", "Manamadurai", "Manapakkam", "Manapparai", "Manavalakurichi", "Mandaikadu", "Mandapam", "Mangadu", "Mangalam", "Mangalampet", "Manimutharu", "Mannargudi", "Mappilaiurani", "Maraimalai Nagar", "Marakkanam", "Maramangalathupatti", "Marandahalli", "Markayankottai", "Marudur", "Marungur", "Masinigudi", "Mathigiri", "Mattur", "Mayiladuthurai", "Mecheri", "Melacheval", "Melachokkanathapuram", "Melagaram", "Melamadai", "Melamaiyur", "Melanattam", "Melathiruppanthuruthi", "Melattur", "Melmananbedu", "Melpattampakkam", "Melur", "Melvisharam", "Mettupalayam", "Mettur", "Meyyanur", "Milavittan", "Minakshipuram", "Minambakkam", "Minjur", "Modakurichi", "Mohanur", "Mopperipalayam", "Mudalur", "Mudichur", "Mudukulathur", "Mukasipidariyur", "Mukkudal", "Mulagumudu", "Mulakaraipatti", "Mulanur", "Mullakkadu", "Muruganpalayam", "Musiri", "Muthupet", "Muthur", "Muttayyapuram", "Muttupet", "Muvarasampettai", "Myladi", "Mylapore", "Nadukkuthagai", "Naduvattam", "Nagapattinam", "Nagavakulam", "Nagercoil", "Nagojanahalli", "Nallampatti", "Nallur", "Namagiripettai", "Namakkal", "Nambiyur", "Nambutalai", "Nandambakkam", "Nandivaram", "Nangavalli", "Nangavaram", "Nanguneri", "Nanjikottai", "Nannilam", "Naranammalpuram", "Naranapuram", "Narasimhanaickenpalayam", "Narasingapuram", "Narasojipatti", "Naravarikuppam", "Nasiyanur", "Natham", "Nathampannai", "Natrampalli", "Nattam", "Nattapettai", "Nattarasankottai", "Navalpattu", "Nazarethpettai", "Nazerath", "Neikkarapatti", "Neiyyur", "Nellikkuppam", "Nelliyalam", "Nemili", "Nemilicheri", "Neripperichal", "Nerkunram", "Nerkuppai", "Nerunjipettai", "Neykkarappatti", "Neyveli", "Nidamangalam", "Nilagiri", "Nilakkottai", "Nilankarai", "Odaipatti", "Odaiyakulam", "Oddanchatram", "Odugathur", "Oggiyamduraipakkam", "Olagadam", "Omalur", "Ooty", "Orathanadu", "Othakadai", "Othakalmandapam", "Ottapparai", "Pacode", "Padaividu", "Padianallur", "Padirikuppam", "Padmanabhapuram", "Padririvedu", "Palaganangudy", "Palaimpatti", "Palakkodu", "Palamedu", "Palani", "Palani Chettipatti", "Palavakkam", "Palavansathu", "Palayakayal", "Palayam", "Palayamkottai", "Palladam", "Pallapalayam", "Pallapatti", "Pallattur", "Pallavaram", "Pallikaranai", "Pallikonda", "Pallipalaiyam", "Pallipalaiyam Agraharam", "Pallipattu", "Pammal", "Panagudi", "Panaimarathupatti", "Panapakkam", "Panboli", "Pandamangalam", "Pannaikadu", "Pannaipuram", "Pannuratti", "Panruti", "Papanasam", "Pappankurichi", "Papparapatti", "Pappireddipatti", "Paramakkudi", "Paramankurichi", "Paramathi", "Parangippettai", "Paravai", "Pasur", "Pathamadai", "Pattinam", "Pattiviranpatti", "Pattukkottai", "Pazhugal", "Pennadam", "Pennagaram", "Pennathur", "Peraiyur", "Peralam", "Perambalur", "Peranamallur", "Peravurani", "Periyakodiveri", "Periyakulam", "Periyanayakkanpalaiyam", "Periyanegamam", "Periyapatti", "Periyasemur", "Pernambut", "Perumagalur", "Perumandi", "Perumuchi", "Perundurai", "Perungalathur", "Perungudi", "Perungulam", "Perur", "Perur Chettipalaiyam", "Pethampalayam", "Pethanaickenpalayam", "Pillanallur", "Pirkankaranai", "Polichalur", "Pollachi", "Polur", "Ponmani", "Ponnamaravathi", "Ponnampatti", "Ponneri", "Porur", "Pothanur", "Pothatturpettai", "Pudukadai", "Pudukkottai Cantonment", "Pudukottai", "Pudupalaiyam Aghraharam", "Pudupalayam", "Pudupatti", "Pudupattinam", "Pudur", "Puduvayal", "Pulambadi", "Pulampatti", "Puliyampatti", "Puliyankudi", "Puliyur", "Pullampadi", "Puluvapatti", "Punamalli", "Punjai Puliyampatti", "Punjai Thottakurichi", "Punjaipugalur", "Puthalam", "Putteri", "Puvalur", "Puzhal", "Puzhithivakkam", "Rajapalayam", "Ramanathapuram", "Ramapuram", "Rameswaram", "Ranipet", "Rasipuram", "Rayagiri", "Rithapuram", "Rosalpatti", "Rudravathi", "Sadayankuppam", "Saint Thomas Mount", "Salangapalayam", "Salem", "Samalapuram", "Samathur", "Sambavar Vadagarai", "Sankaramanallur", "Sankarankoil", "Sankarapuram", "Sankari", "Sankarnagar", "Saravanampatti", "Sarcarsamakulam", "Sathiyavijayanagaram", "Sathuvachari", "Sathyamangalam", "Sattankulam", "Sattur", "Sayalgudi", "Sayapuram", "Seithur", "Sembakkam", "Semmipalayam", "Sennirkuppam", "Senthamangalam", "Sentharapatti", "Senur", "Sethiathoppu", "Sevilimedu", "Sevugampatti", "Shenbakkam", "Shencottai", "Shenkottai", "Sholavandan", "Sholinganallur", "Sholingur", "Sholur", "Sikkarayapuram", "Singampuneri", "Singanallur", "Singaperumalkoil", "Sirapalli", "Sirkali", "Sirugamani", "Sirumugai", "Sithayankottai", "Sithurajapuram", "Sivaganga", "Sivagiri", "Sivakasi", "Sivanthipuram", "Sivur", "Soranjeri", "South Kannanur", "South Kodikulam", "Srimushnam", "Sriperumpudur", "Sriramapuram", "Srirangam", "Srivaikuntam", "Srivilliputtur", "Suchindram", "Suliswaranpatti", "Sulur", "Sundarapandiam", "Sundarapandiapuram", "Surampatti", "Surandai", "Suriyampalayam", "Swamimalai", "TNPL Pugalur", "Tambaram", "Taramangalam", "Tattayyangarpettai", "Tayilupatti", "Tenkasi", "Thadikombu", "Thakkolam", "Thalainayar", "Thalakudi", "Thamaraikulam", "Thammampatti", "Thanjavur", "Thanthoni", "Tharangambadi", "Thedavur", "Thenambakkam", "Thengampudur", "Theni", "Theni Allinagaram", "Thenkarai", "Thenthamaraikulam", "Thenthiruperai", "Thesur", "Thevaram", "Thevur", "Thiagadurgam", "Thiagarajar Colony", "Thingalnagar", "Thiruchirapalli", "Thirukarungudi", "Thirukazhukundram", "Thirumalayampalayam", "Thirumazhisai", "Thirunagar", "Thirunageswaram", "Thirunindravur", "Thirunirmalai", "Thiruparankundram", "Thiruparappu", "Thiruporur", "Thiruppanandal", "Thirupuvanam", "Thiruthangal", "Thiruthuraipundi", "Thiruvaivaru", "Thiruvalam", "Thiruvarur", "Thiruvattaru", "Thiruvenkatam", "Thiruvennainallur", "Thiruvithankodu", "Thisayanvilai", "Thittacheri", "Thondamuthur", "Thorapadi", "Thottipalayam", "Thottiyam", "Thudiyalur", "Thuthipattu", "Thuvakudi", "Timiri", "Tindivanam", "Tinnanur", "Tiruchchendur", "Tiruchengode", "Tirukkalukkundram", "Tirukkattuppalli", "Tirukkoyilur", "Tirumangalam", "Tirumullaivasal", "Tirumuruganpundi", "Tirunageswaram", "Tirunelveli", "Tirupathur", "Tirupattur", "Tiruppuvanam", "Tirupur", "Tirusulam", "Tiruttani", "Tiruvallur", "Tiruvannamalai", "Tiruverambur", "Tiruverkadu", "Tiruvethipuram", "Tiruvidaimarudur", "Tiruvottiyur", "Tittakudi", "Tondi", "Turaiyur", "Tuticorin", "Udagamandalam", "Udagamandalam Valley", "Udankudi", "Udayarpalayam", "Udumalaipettai", "Udumalpet", "Ullur", "Ulundurpettai", "Unjalaur", "Unnamalaikadai", "Uppidamangalam", "Uppiliapuram", "Urachikkottai", "Urapakkam", "Usilampatti", "Uthangarai", "Uthayendram", "Uthiramerur", "Uthukkottai", "Uttamapalaiyam", "Uttukkuli", "Vadakarai Kizhpadugai", "Vadakkanandal", "Vadakku Valliyur", "Vadalur", "Vadamadurai", "Vadavalli", "Vadipatti", "Vadugapatti", "Vaithiswarankoil", "Valangaiman", "Valasaravakkam", "Valavanur", "Vallam", "Valparai", "Valvaithankoshtam", "Vanavasi", "Vandalur", "Vandavasi", "Vandiyur", "Vaniputhur", "Vaniyambadi", "Varadarajanpettai", "Varadharajapuram", "Vasudevanallur", "Vathirairuppu", "Vattalkundu", "Vazhapadi", "Vedapatti", "Vedaranniyam", "Vedasandur", "Velampalaiyam", "Velankanni", "Vellakinar", "Vellakoil", "Vellalapatti", "Vellalur", "Vellanur", "Vellimalai", "Vellore", "Vellottamparappu", "Velluru", "Vengampudur", "Vengathur", "Vengavasal", "Venghatur", "Venkarai", "Vennanthur", "Veppathur", "Verkilambi", "Vettaikaranpudur", "Vettavalam", "Vijayapuri", "Vikramasingapuram", "Vikravandi", "Vilangudi", "Vilankurichi", "Vilapakkam", "Vilathikulam", "Vilavur", "Villukuri", "Villupuram", "Viraganur", "Virakeralam", "Virakkalpudur", "Virapandi", "Virapandi Cantonment", "Virappanchatram", "Viravanallur", "Virudambattu", "Virudhachalam", "Virudhunagar", "Virupakshipuram", "Viswanatham", "Vriddhachalam", "Walajabad", "Walajapet", "Wellington", "Yercaud", "Zamin Uthukuli", "Achampet", "Adilabad", "Armoor", "Asifabad", "Badepally", "Banswada", "Bellampalli", "Bhadrachalam", "Bhainsa", "Bhongir", "Bhupalpally", "Bodhan", "Bollaram", "Devarkonda", "Farooqnagar", "Gadwal", "Gajwel", "Ghatkesar", "Hyderabad", "Jagtial", "Jangaon", "Kagaznagar", "Kalwakurthy", "Kamareddy", "Karimnagar", "Khammam", "Kodada", "Koratla", "Kottagudem", "Kyathampalle", "Madhira", "Mahabubabad", "Mahbubnagar", "Mancherial", "Mandamarri", "Manuguru", "Medak", "`city_name`", "Medchal", "Miryalaguda", "Nagar Karnul", "Nakrekal", "Nalgonda", "Narayanpet", "Narsampet", "Nirmal", "Nizamabad", "Palwancha", "Peddapalli", "Ramagundam", "Ranga Reddy district", "Sadasivpet", "Sangareddy", "Sarapaka", "Sathupalle", "Secunderabad", "Siddipet", "Singapur", "Sircilla", "Suryapet", "Tandur", "Vemulawada", "Vikarabad", "Wanaparthy", "Warangal", "Yellandu", "Zahirabad", "Agartala", "Amarpur", "Ambassa", "Badharghat", "Belonia", "Dharmanagar", "Gakulnagar", "Gandhigram", "Indranagar", "Jogendranagar", "Kailasahar", "Kamalpur", "Kanchanpur", "Khowai", "Kumarghat", "Kunjaban", "Narsingarh", "Pratapgarh", "Ranir Bazar", "Sabrum", "Sonamura", "Teliamura", "Udaipur", "Achhalda", "Achhnera", "Adari", "Afzalgarh", "Agarwal Mandi", "Agra", "Agra Cantonment", "Ahraura", "Ailum", "Air Force Area", "Ajhuwa", "Akbarpur", "Alapur", "Aliganj", "Aligarh", "Allahabad", "Allahabad Cantonment", "Allahganj", "Amanpur", "Ambahta", "Amethi", "Amila", "Amilo", "Aminagar Sarai", "Aminagar Urf Bhurbaral", "Amraudha", "Amroha", "Anandnagar", "Anpara", "Antu", "Anupshahr", "Aonla", "Armapur Estate", "Ashokpuram", "Ashrafpur Kichhauchha", "Atarra", "Atasu", "Atrauli", "Atraulia", "Auraiya", "Aurangabad", "Aurangabad Bangar", "Auras", "Awagarh", "Ayodhya", "Azamgarh", "Azizpur", "Azmatgarh", "Babarpur Ajitmal", "Baberu", "Babina", "Babrala", "Babugarh", "Bachhiowan", "Bachhraon", "Bad", "Badaun", "Baghpat", "Bah", "Bahadurganj", "Baheri", "Bahjoi", "Bahraich", "Bahsuma", "Bahua", "Bajna", "Bakewar", "Bakiabad", "Baldeo", "Ballia", "Balrampur", "Banat", "Banda", "Bangarmau", "Banki", "Bansdih", "Bansgaon", "Bansi", "Barabanki", "Baragaon", "Baraut", "Bareilly", "Bareilly Cantonment", "Barhalganj", "Barhani", "Barhapur", "Barkhera", "Barsana", "Barva Sagar", "Barwar", "Basti", "Begumabad Budhana", "Behat", "Behta Hajipur", "Bela", "Belthara", "Beniganj", "Beswan", "Bewar", "Bhadarsa", "Bhadohi", "Bhagwantnagar", "Bharatganj", "Bhargain", "Bharthana", "Bharuhana", "Bharwari", "Bhatni Bazar", "Bhatpar Rani", "Bhawan Bahadurnagar", "Bhinga", "Bhojpur Dharampur", "Bhokarhedi", "Bhongaon", "Bhulepur", "Bidhuna", "Bighapur", "Bijnor", "Bijpur", "Bikapur", "Bilari", "Bilaspur", "Bilgram", "Bilhaur", "Bilram", "Bilrayaganj", "Bilsanda", "Bilsi", "Bindki", "Bisalpur", "Bisanda Buzurg", "Bisauli", "Bisharatganj", "Bisokhar", "Biswan", "Bithur", "Budaun", "Bugrasi", "Bulandshahar", "Burhana", "Chail", "Chak Imam Ali", "Chakeri", "Chakia", "Chandauli", "Chandausi", "Chandpur", "Charkhari", "Charthawal", "Chaumuhan", "Chhaprauli", "Chhara Rafatpur", "Chharprauli", "Chhata", "Chhatari", "Chhibramau", "Chhutmalpur", "Chilkana Sultanpur", "Chirgaon", "Chit Baragaon", "Chitrakut Dham", "Chopan", "Choubepur Kalan", "Chunar", "Churk Ghurma", "Colonelganj", "Dadri", "Dalmau", "Dankaur", "Dariyabad", "Dasna", "Dataganj", "Daurala", "Dayal Bagh", "Deoband", "Deoranian", "Deoria", "Dewa", "Dhampur", "Dhanauha", "Dhanauli", "Dhanaura", "Dharoti Khurd", "Dhauratanda", "Dhaurhra", "Dibai", "Dibiyapur", "Dildarnagar Fatehpur", "Do Ghat", "Dohrighat", "Dostpur", "Dudhinagar", "Dulhipur", "Dundwaraganj", "Ekdil", "Erich", "Etah", "Etawah", "Faizabad", "Faizabad Cantonment", "Faizganj", "Farah", "Faridnagar", "Faridpur", "Faridpur Cantonment", "Fariha", "Farrukhabad", "Fatehabad", "Fatehganj Pashchimi", "Fatehganj Purvi", "Fatehgarh", "Fatehpur", "Fatehpur Chaurasi", "Fatehpur Sikri", "Firozabad", "Gajraula", "Ganga Ghat", "Gangapur", "Gangoh", "Ganj Muradabad", "Garautha", "Garhi Pukhta", "Garhmukteshwar", "Gaura Barahaj", "Gauri Bazar", "Gausganj", "Gawan", "Ghatampur", "Ghaziabad", "Ghazipur", "Ghiror", "Ghorawal", "Ghosi", "Ghosia Bazar", "Ghughuli", "Gohand", "Gokul", "Gola Bazar", "Gola Gokarannath", "Gonda", "Gopamau", "Gopiganj", "Gorakhpur", "Gosainganj", "Govardhan", "Greater Noida", "Gulaothi", "Gulariya", "Gulariya Bhindara", "Gunnaur", "Gursahaiganj", "Gursarai", "Gyanpur", "Hafizpur", "Haidergarh", "Haldaur", "Hamirpur", "Handia", "Hapur", "Hardoi", "Harduaganj", "Hargaon", "Hariharpur", "Harraiya", "Hasanpur", "Hasayan", "Hastinapur", "Hata", "Hathras", "Hyderabad", "Ibrahimpur", "Iglas", "Ikauna", "Iltifatganj Bazar", "Indian Telephone Industry Mank", "Islamnagar", "Itaunja", "Itimadpur", "Jagner", "Jahanabad", "Jahangirabad", "Jahangirpur", "Jais", "Jaithara", "Jalalabad", "Jalali", "Jalalpur", "Jalaun", "Jalesar", "Jamshila", "Jangipur", "Jansath", "Jarwal", "Jasrana", "Jaswantnagar", "Jatari", "Jaunpur", "Jewar", "Jhalu", "Jhansi", "Jhansi Cantonment", "Jhansi Railway Settlement", "Jhinjhak", "Jhinjhana", "Jhusi", "Jhusi Kohna", "Jiyanpur", "Joya", "Jyoti Khuria", "Jyotiba Phule Nagar", "Kabrai", "Kachhauna Patseni", "Kachhla", "Kachhwa", "Kadaura", "Kadipur", "Kailashpur", "Kaimganj", "Kairana", "Kakgaina", "Kakod", "Kakori", "Kakrala", "Kalinagar", "Kalpi", "Kamalganj", "Kampil", "Kandhla", "Kandwa", "Kannauj", "Kanpur", "Kant", "Kanth", "Kaptanganj", "Karaon", "Karari", "Karhal", "Karnawal", "Kasganj", "Katariya", "Katghar Lalganj", "Kathera", "Katra", "Katra Medniganj", "Kauriaganj", "Kemri", "Kerakat", "Khadda", "Khaga", "Khailar", "Khair", "Khairabad", "Khairagarh", "Khalilabad", "Khamaria", "Khanpur", "Kharela", "Khargupur", "Khariya", "Kharkhoda", "Khatauli", "Khatauli Rural", "Khekra", "Kheri", "Kheta Sarai", "Khudaganj", "Khurja", "Khutar", "Kiraoli", "Kiratpur", "Kishanpur", "Kishni", "Kithaur", "Koiripur", "Konch", "Kopaganj", "Kora Jahanabad", "Korwa", "Kosi Kalan", "Kota", "Kotra", "Kotwa", "Kulpahar", "Kunda", "Kundarki", "Kunwargaon", "Kurara", "Kurawali", "Kursath", "Kurthi Jafarpur", "Kushinagar", "Kusmara", "Laharpur", "Lakhimpur", "Lakhna", "Lalganj", "Lalitpur", "Lar", "Lawar", "Ledwa Mahuwa", "Lohta", "Loni", "Lucknow", "Machhlishahr", "Madhoganj", "Madhogarh", "Maghar", "Mahaban", "Maharajganj", "Mahmudabad", "Mahoba", "Maholi", "Mahona", "Mahroni", "Mailani", "Mainpuri", "Majhara Pipar Ehatmali", "Majhauli Raj", "Malihabad", "Mallanwam", "Mandawar", "Manikpur", "Maniyar", "Manjhanpur", "Mankapur", "Marehra", "Mariahu", "Maruadih", "Maswasi", "Mataundh", "Mathu", "Mathura", "Mathura Cantonment", "Mau", "Mau Aima", "Maudaha", "Mauranipur", "Maurawan", "Mawana", "Meerut", "Mehnagar", "Mehndawal", "Mendu", "Milak", "Miranpur", "Mirat", "Mirat Cantonment", "Mirganj", "Mirzapur", "Misrikh", "Modinagar", "Mogra Badshahpur", "Mohan", "Mohanpur", "Mohiuddinpur", "Moradabad", "Moth", "Mubarakpur", "Mughal Sarai", "Mughal Sarai Railway Settlemen", "Muhammadabad", "Muhammadi", "Mukrampur Khema", "Mundia", "Mundora", "Muradnagar", "Mursan", "Musafirkhana", "Muzaffarnagar", "Nadigaon", "Nagina", "Nagram", "Nai Bazar", "Nainana Jat", "Najibabad", "Nakur", "Nanaunta", "Nandgaon", "Nanpara", "Naraini", "Narauli", "Naraura", "Naugawan Sadat", "Nautanwa", "Nawabganj", "Nichlaul", "Nidhauli Kalan", "Nihtaur", "Nindaura", "Niwari", "Nizamabad", "Noida", "Northern Railway Colony", "Nurpur", "Nyoria Husenpur", "Nyotini", "Obra", "Oel Dhakwa", "Orai", "Oran", "Ordinance Factory Muradnagar", "Pachperwa", "Padrauna", "Pahasu", "Paintepur", "Pali", "Palia Kalan", "Parasi", "Parichha", "Parichhatgarh", "Parsadepur", "Patala", "Patiyali", "Patti", "Pawayan", "Phalauda", "Phaphund", "Phulpur", "Phulwaria", "Pihani", "Pilibhit", "Pilkana", "Pilkhuwa", "Pinahat", "Pipalsana Chaudhari", "Pipiganj", "Pipraich", "Pipri", "Pratapgarh", "Pukhrayan", "Puranpur", "Purdil Nagar", "Purqazi", "Purwa", "Qasimpur", "Rabupura", "Radha Kund", "Rae Bareilly", "Raja Ka Rampur", "Rajapur", "Ramkola", "Ramnagar", "Rampur", "Rampur Bhawanipur", "Rampur Karkhana", "Rampur Maniharan", "Rampura", "Ranipur", "Rashidpur Garhi", "Rasra", "Rasulabad", "Rath", "Raya", "Renukut", "Reoti", "Richha", "Risia Bazar", "Rithora", "Robertsganj", "Roza", "Rudarpur", "Rudauli", "Rudayan", "Rura", "Rustamnagar Sahaspur", "Sabatwar", "Sadabad", "Sadat", "Safipur", "Sahanpur", "Saharanpur", "Sahaspur", "Sahaswan", "Sahawar", "Sahibabad", "Sahjanwa", "Sahpau", "Saidpur", "Sainthal", "Saiyadraja", "Sakhanu", "Sakit", "Salarpur Khadar", "Salimpur", "Salon", "Sambhal", "Sambhawali", "Samdhan", "Samthar", "Sandi", "Sandila", "Sarai Mir", "Sarai akil", "Sarauli", "Sardhana", "Sarila", "Sarsawan", "Sasni", "Satrikh", "Saunkh", "Saurikh", "Seohara", "Sewal Khas", "Sewarhi", "Shahabad", "Shahganj", "Shahi", "Shahjahanpur", "Shahjahanpur Cantonment", "Shahpur", "Shamli", "Shamsabad", "Shankargarh", "Shergarh", "Sherkot", "Shikarpur", "Shikohabad", "Shisgarh", "Shivdaspur", "Shivli", "Shivrajpur", "Shohratgarh", "Siddhanur", "Siddharthnagar", "Sidhauli", "Sidhpura", "Sikandarabad", "Sikandarpur", "Sikandra", "Sikandra Rao", "Singahi Bhiraura", "Sirathu", "Sirsa", "Sirsaganj", "Sirsi", "Sisauli", "Siswa Bazar", "Sitapur", "Siyana", "Som", "Sonbhadra", "Soron", "Suar", "Sukhmalpur Nizamabad", "Sultanpur", "Sumerpur", "Suriyawan", "Swamibagh", "Tajpur", "Talbahat", "Talgram", "Tambaur", "Tanda", "Tatarpur Lallu", "Tetribazar", "Thakurdwara", "Thana Bhawan", "Thiriya Nizamat Khan", "Tikaitnagar", "Tikri", "Tilhar", "Tindwari", "Tirwaganj", "Titron", "Tori Fatehpur", "Tulsipur", "Tundla", "Tundla Kham", "Tundla Railway Colony", "Ugu", "Ujhani", "Ujhari", "Umri", "Umri Kalan", "Un", "Unchahar", "Unnao", "Usaihat", "Usawan", "Utraula", "Varanasi", "Varanasi Cantonment", "Vijaigarh", "Vrindavan", "Wazirganj", "Zafarabad", "Zaidpur", "Zamania", "Almora", "Almora Cantonment", "Badrinathpuri", "Bageshwar", "Bah Bazar", "Banbasa", "Bandia", "Barkot", "Bazpur", "Bhim Tal", "Bhowali", "Chakrata", "Chamba", "Chamoli and Gopeshwar", "Champawat", "Clement Town", "Dehra Dun Cantonment", "Dehradun", "Dehrakhas", "Devaprayag", "Dhaluwala", "Dhandera", "Dharchula", "Dharchula Dehat", "Didihat", "Dineshpur", "Doiwala", "Dugadda", "Dwarahat", "Gadarpur", "Gangotri", "Gauchar", "Haldwani", "Haridwar", "Herbertpur", "Jaspur", "Jhabrera", "Joshimath", "Kachnal Gosain", "Kaladungi", "Kalagarh", "Karnaprayang", "Kashipur", "Kashirampur", "Kausani", "Kedarnath", "Kelakhera", "Khatima", "Kichha", "Kirtinagar", "Kotdwara", "Laksar", "Lalkuan", "Landaura", "Landhaura Cantonment", "Lensdaun", "Logahat", "Mahua Dabra Haripura", "Mahua Kheraganj", "Manglaur", "Masuri", "Mohanpur Mohammadpur", "Muni Ki Reti", "Nagla", "Nainital", "Nainital Cantonment", "Nandaprayang", "Narendranagar", "Pauri", "Pithoragarh", "Pratitnagar", "Raipur", "Raiwala", "Ramnagar", "Ranikhet", "Ranipur", "Rishikesh", "Rishikesh Cantonment", "Roorkee", "Rudraprayag", "Rudrapur", "Rurki", "Rurki Cantonment", "Shaktigarh", "Sitarganj", "Srinagar", "Sultanpur", "Tanakpur", "Tehri", "Udham Singh Nagar", "Uttarkashi", "Vikasnagar", "Virbhadra", "24 Parganas (n)", "24 Parganas (s)", "Adra", "Ahmadpur", "Aiho", "Aistala", "Alipur Duar", "Alipur Duar Railway Junction", "Alpur", "Amalhara", "Amkula", "Amlagora", "Amodghata", "Amtala", "Andul", "Anksa", "Ankurhati", "Anup Nagar", "Arambagh", "Argari", "Arsha", "Asansol", "Ashoknagar Kalyangarh", "Aurangabad", "Bablari Dewanganj", "Badhagachhi", "Baduria", "Baghdogra", "Bagnan", "Bagra", "Bagula", "Baharampur", "Bahirgram", "Bahula", "Baidyabati", "Bairatisal", "Baj Baj", "Bakreswar", "Balaram Pota", "Balarampur", "Bali Chak", "Ballavpur", "Bally", "Balurghat", "Bamunari", "Banarhat Tea Garden", "Bandel", "Bangaon", "Bankra", "Bankura", "Bansbaria", "Banshra", "Banupur", "Bara Bamonia", "Barakpur", "Barakpur Cantonment", "Baranagar", "Barasat", "Barddhaman", "Barijhati", "Barjora", "Barrackpore", "Baruihuda", "Baruipur", "Barunda", "Basirhat", "Baska", "Begampur", "Beldanga", "Beldubi", "Belebathan", "Beliator", "Bhadreswar", "Bhandardaha", "Bhangar Raghunathpur", "Bhangri Pratham Khanda", "Bhanowara", "Bhatpara", "Bholar Dabri", "Bidhannagar", "Bidyadharpur", "Biki Hakola", "Bilandapur", "Bilpahari", "Bipra Noapara", "Birlapur", "Birnagar", "Bisarpara", "Bishnupur", "Bolpur", "Bongaon", "Bowali", "Burdwan", "Canning", "Cart Road", "Chachanda", "Chak Bankola", "Chak Enayetnagar", "Chak Kashipur", "Chakalampur", "Chakbansberia", "Chakdaha", "Chakpara", "Champahati", "Champdani", "Chamrail", "Chandannagar", "Chandpur", "Chandrakona", "Chapari", "Chapui", "Char Brahmanagar", "Char Maijdia", "Charka", "Chata Kalikapur", "Chauhati", "Checha Khata", "Chelad", "Chhora", "Chikrand", "Chittaranjan", "Contai", "Cooch Behar", "Dainhat", "Dakshin Baguan", "Dakshin Jhapardaha", "Dakshin Rajyadharpur", "Dakshin Raypur", "Dalkola", "Dalurband", "Darap Pur", "Darjiling", "Daulatpur", "Debipur", "Defahat", "Deora", "Deulia", "Dhakuria", "Dhandadihi", "Dhanyakuria", "Dharmapur", "Dhatri Gram", "Dhuilya", "Dhulagari", "Dhulian", "Dhupgari", "Dhusaripara", "Diamond Harbour", "Digha", "Dignala", "Dinhata", "Dubrajpur", "Dumjor", "Durgapur", "Durllabhganj", "Egra", "Eksara", "Falakata", "Farakka", "Fatellapur", "Fort Gloster", "Gabberia", "Gadigachha", "Gairkata", "Gangarampur", "Garalgachha", "Garbeta Amlagora", "Garhbeta", "Garshyamnagar", "Garui", "Garulia", "Gayespur", "Ghatal", "Ghorsala", "Goaljan", "Goasafat", "Gobardanga", "Gobindapur", "Gopalpur", "Gopinathpur", "Gora Bazar", "Guma", "Gurdaha", "Guriahati", "Guskhara", "Habra", "Haldia", "Haldibari", "Halisahar", "Haora", "Harharia Chak", "Harindanga", "Haringhata", "Haripur", "Harishpur", "Hatgachha", "Hatsimla", "Hijuli", "Hindustan Cables Town", "Hooghly", "Howrah", "Hugli-Chunchura", "Humaipur", "Ichha Pur Defence Estate", "Ingraj Bazar", "Islampur", "Jafarpur", "Jagadanandapur", "Jagdishpur", "Jagtaj", "Jala Kendua", "Jaldhaka", "Jalkhura", "Jalpaiguri", "Jamuria", "Jangipur", "Jaygaon", "Jaynagar-Majilpur", "Jemari", "Jemari Township", "Jetia", "Jhalida", "Jhargram", "Jhorhat", "Jiaganj-Azimganj", "Joka", "Jot Kamal", "Kachu Pukur", "Kajora", "Kakdihi", "Kakdwip", "Kalaikunda", "Kalara", "Kalimpong", "Kaliyaganj", "Kalna", "Kalyani", "Kamarhati", "Kanaipur", "Kanchrapara", "Kandi", "Kanki", "Kankuria", "Kantlia", "Kanyanagar", "Karimpur", "Karsiyang", "Kasba", "Kasimbazar", "Katwa", "Kaugachhi", "Kenda", "Kendra Khottamdi", "Kendua", "Kesabpur", "Khagrabari", "Khalia", "Khalor", "Khandra", "Khantora", "Kharagpur", "Kharagpur Railway Settlement", "Kharar", "Khardaha", "Khari Mala Khagrabari", "Kharsarai", "Khatra", "Khodarampur", "Kodalia", "Kolaghat", "Kolaghat Thermal Power Project", "Kolkata", "Konardihi", "Konnogar", "Krishnanagar", "Krishnapur", "Kshidirpur", "Kshirpai", "Kulihanda", "Kulti", "Kunustara", "Kuperskem", "Madanpur", "Madhusudanpur", "Madhyamgram", "Maheshtala", "Mahiari", "Mahikpur", "Mahira", "Mahishadal", "Mainaguri", "Makardaha", "Mal", "Malda", "Mandarbani", "Mansinhapur", "Masila", "Maslandapur", "Mathabhanga", "Mekliganj", "Memari", "Midnapur", "Mirik", "Monoharpur", "Mrigala", "Muragachha", "Murgathaul", "Murshidabad", "Nabadhai Dutta Pukur", "Nabagram", "Nabgram", "Nachhratpur Katabari", "Nadia", "Naihati", "Nalhati", "Nasra", "Natibpur", "Naupala", "Navadwip", "Nebadhai Duttapukur", "New Barrackpore", "Ni Barakpur", "Nibra", "Noapara", "Nokpul", "North Barakpur", "Odlabari", "Old Maldah", "Ondal", "Pairagachha", "Palashban", "Panchla", "Panchpara", "Pandua", "Pangachhiya", "Paniara", "Panihati", "Panuhat", "Par Beliya", "Parashkol", "Parasia", "Parbbatipur", "Parui", "Paschim Jitpur", "Paschim Punro Para", "Patrasaer", "Pattabong Tea Garden", "Patuli", "Patulia", "Phulia", "Podara", "Port Blair", "Prayagpur", "Pujali", "Purba Medinipur", "Purba Tajpur", "Purulia", "Raghudebbati", "Raghudebpur", "Raghunathchak", "Raghunathpur", "Raghunathpur-Dankuni", "Raghunathpur-Magra", "Raigachhi", "Raiganj", "Raipur", "Rajarhat Gopalpur", "Rajpur", "Ramchandrapur", "Ramjibanpur", "Ramnagar", "Rampur Hat", "Ranaghat", "Raniganj", "Ratibati", "Raypur", "Rishra", "Rishra Cantonment", "Ruiya", "Sahajadpur", "Sahapur", "Sainthia", "Salap", "Sankarpur", "Sankrail", "Santoshpur", "Saontaidih", "Sarenga", "Sarpi", "Satigachha", "Serpur", "Shankhanagar", "Shantipur", "Shrirampur", "Siduli", "Siliguri", "Simla", "Singur", "Sirsha", "Siuri", "Sobhaganj", "Sodpur", "Sonamukhi", "Sonatikiri", "Srikantabati", "Srirampur", "Sukdal", "Taherpur", "Taki", "Talbandha", "Tamluk", "Tarakeswar", "Tentulberia", "Tentulkuli", "Thermal Power Project", "Tinsukia", "Titagarh", "Tufanganj", "Ukhra", "Ula", "Ulubaria", "Uttar Durgapur", "Uttar Goara", "Uttar Kalas", "Uttar Kamakhyaguri", "Uttar Latabari", "Uttar Mahammadpur", "Uttar Pirpur", "Uttar Raypur", "Uttarpara-Kotrung"};
    String[] states = {"Andaman and Nicobar Islands",
            "Andhra Pradesh",
            "Arunachal Pradesh",
            "Assam",
            "Bihar",
            "Chandigarh",
            "Chhattisgarh",
            "Dadra and Nagar Haveli",
            "Daman and Diu",
            "Delhi",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jammu and Kashmir",
            "Jharkhand",
            "Karnataka",
            "Kenmore",
            "Kerala",
            "Lakshadweep",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "Narora",
            "Natwar",
            "Odisha",
            "Paschim Medinipur",
            "Pondicherry",
            "Punjab",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Telangana",
            "Tripura",
            "Uttar Pradesh",
            "Uttarakhand",
            "Vaishali",
            "West Bengal",
    };
    private ImageView imageView;
    private FirebaseStorage storage;
    private RadioGroup RG;
    private StorageReference storageReference;
    private Uri filePath;
    private DrawerLayout mDrawerLayout;
    private final int PICK_IMAGE_REQUEST = 71;
    private final int CAPTURE_IMAGE_REQUEST = 86;
    private AutoCompleteTextView textView, stateView;
    private String[] add;
    private GoogleMap mMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private double plotlat;
    private double plotlng;
    private ProgressDialog progressDialog;
    private int backButtonCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_have_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        /*try {
            HomeData homeData = (HomeData) getIntent().getSerializableExtra("homedata");
            if (homeData != null) {
                Toast.makeText(this, homeData.PhoneNo, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }*/
        final HomeData homeData = (HomeData) getIntent().getSerializableExtra("homeData");
        progressDialog = new ProgressDialog(HaveHome.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if (location != null) {
                            double lat = location.getLatitude();
                            double lng = location.getLongitude();
                            LatLng loc = new LatLng(lat, lng);
                            if (homeData == null) {
                                plotlat = lat;
                                plotlng = lng;
                            } else {
                                plotlat = homeData.lat;
                                plotlng = homeData.lng;
                            }

                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(loc)
                                    .zoom(18)
                                    .build();
                            int height = 50;
                            int width = 60;
                            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.mipmap.plot);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                                    .position(loc).draggable(true));
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                                    2000, null);
                            if (homeData == null)
                                add(lat, lng);
                            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                                @Override
                                public void onMarkerDragStart(Marker marker) {

                                }

                                @Override
                                public void onMarkerDrag(Marker marker) {

                                }

                                @Override
                                public void onMarkerDragEnd(Marker marker) {
                                    // mMap.clear();
                                    //mMap.addMarker(new MarkerOptions()
                                    //              .position(marker.getPosition()));
                                    plotlat = marker.getPosition().latitude;
                                    plotlng = marker.getPosition().longitude;

                                    mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                                    add(plotlat, plotlng);

                                }
                            });
                            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 18));
                            // mMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);


                        } else {
                            Toast.makeText(HaveHome.this, "yahan", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                };

                LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 100, locationListener);

            }
        });

        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrview);
        ((mapfrag) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new mapfrag.OnTouchListener() {
            @Override
            public void onTouch() {
                scrollView.requestDisallowInterceptTouchEvent(true);
            }
        });


        btnChoose = (Button) findViewById(R.id.btnChoose);

        imageView = (ImageView) findViewById(R.id.imgView);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, cities);

        ArrayAdapter<String> adapterS = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, states);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, cities);
        textView = (AutoCompleteTextView)
                findViewById(R.id.city);
        stateView = (AutoCompleteTextView)
                findViewById(R.id.state);
        textView.setAdapter(adapter);
        stateView.setAdapter(adapterS);
        stateView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    String str = stateView.getText().toString();
                    ListAdapter listAdapter = stateView.getAdapter();
                    for (int i = 0; i < listAdapter.getCount(); i++) {
                        String temp = listAdapter.getItem(i).toString();
                        if (str.compareTo(temp) == 0) {
                            return;
                        }
                    }

                    stateView.setText("");
                }
            }
        });
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    String str = textView.getText().toString();
                    ListAdapter listAdapter = textView.getAdapter();
                    for (int i = 0; i < listAdapter.getCount(); i++) {
                        String temp = listAdapter.getItem(i).toString();
                        if (str.compareTo(temp) == 0) {
                            return;
                        }
                    }

                    textView.setText("");
                }
            }
        });
        actionbar.setHomeAsUpIndicator(R.mipmap.menu1);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        if (menuItem.getTitle().equals("Dashboard")) {
                            Intent i = new Intent(HaveHome.this, MainActivity.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Have Home")) {
                            Intent i = new Intent(HaveHome.this, HaveHome.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Need Home")) {
                            Intent i = new Intent(HaveHome.this, Filter.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("Profile")) {
                            Intent i = new Intent(HaveHome.this, Profile.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My preferences")) {
                            Intent i = new Intent(HaveHome.this, preferences.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("About us")) {
                            Intent i = new Intent(HaveHome.this, aboutus.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My shortlists")) {
                            Intent i = new Intent(HaveHome.this, Shortlist.class);
                            startActivity(i);
                        } else if (menuItem.getTitle().equals("My Houses")) {
                            Intent i = new Intent(HaveHome.this, Uploads.class);
                            startActivity(i);
                        }
                        return true;
                    }
                });
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(),"AIzaSyAK1RvR5xPY8xLsK66P_aCoVYKqbIZs6hc", Locale.US);
        }
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

// Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);

        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        if (homeData != null) {
            Bundle bundle = getIntent().getExtras();
            String Plot = bundle.getString("plotData");
            String[] add = Plot.split("/");
            ET3 = (EditText) findViewById(R.id.area);
            ET4 = (EditText) findViewById(R.id.plot);
            ET5 = (EditText) findViewById(R.id.price);
            ET6 = (EditText) findViewById(R.id.agreement);
            ET7 = (EditText) findViewById(R.id.bedrooms);
            ET8 = (EditText) findViewById(R.id.bathrooms);
            ET9 = (EditText) findViewById(R.id.toilets);
//        ET10 = (EditText) findViewById(R.id.wardrobe);
//        ET11 = (EditText) findViewById(R.id.beds);
//        ET12 = (EditText) findViewById(R.id.fans);
//        ET13 = (EditText) findViewById(R.id.lights);
//        ET14 = (EditText) findViewById(R.id.modularkitchen);
//        ET15 = (EditText) findViewById(R.id.ac);
//        ET16 = (EditText) findViewById(R.id.inverter);
            ET17 = (EditText) findViewById(R.id.phoneNo);
            CB10 = (CheckBox) findViewById(R.id.wardrobe);
            CB11 = (CheckBox) findViewById(R.id.beds);
            CB12 = (CheckBox) findViewById(R.id.fans);
            CB13 = (CheckBox) findViewById(R.id.lights);
            CB14 = (CheckBox) findViewById(R.id.modularkitchen);
            CB15 = (CheckBox) findViewById(R.id.ac);
            CB16 = (CheckBox) findViewById(R.id.inverter);
            RG = (RadioGroup) findViewById(R.id.rentto);
            textView = (AutoCompleteTextView) findViewById(R.id.city);
            stateView = (AutoCompleteTextView) findViewById(R.id.state);
            ET4.setText(add[3]);
            ET3.setText(add[2]);
            ET5.setText(homeData.Price);
            ET6.setText(homeData.Agreement);
            ET7.setText(homeData.BedRooms);
            ET8.setText(homeData.BathRooms);
            ET9.setText(homeData.Toilets);
            ET17.setText(homeData.PhoneNo);
            stateView.setText(add[0]);
            textView.setText(add[1]);
            RadioButton RB1, RB2, RB3;
            RB1 = (RadioButton) findViewById(R.id.family);
            RB2 = (RadioButton) findViewById(R.id.singlemen);
            RB3 = (RadioButton) findViewById(R.id.singlewomen);
            if (homeData.WardRobe.equals("Yes")) {
                CB10.setChecked(true);
            }
            if (homeData.Beds.equals("Yes")) {
                CB11.setChecked(true);
            }
            if (homeData.Fans.equals("Yes")) {
                CB12.setChecked(true);
            }
            if (homeData.Lights.equals("Yes")) {
                CB13.setChecked(true);
            }
            if (homeData.ModularKitchen.equals("Yes")) {
                CB14.setChecked(true);
            }
            if (homeData.AC.equals("Yes")) {
                CB15.setChecked(true);
            }
            if (homeData.Inverter.equals("Yes")) {
                CB16.setChecked(true);
            }
            if (homeData.RentTo.equals("Family")) {
                RB1.setSelected(true);
                RB1.setChecked(true);
            } else if (homeData.RentTo.equals("Single Men")) {
                RB2.setSelected(true);
                RB2.setChecked(true);
            } else if (homeData.RentTo.equals("Single Women")) {
                RB3.setSelected(true);
                RB3.setChecked(true);
            }
            ImageView photoImageView = (ImageView) findViewById(R.id.imgView);
            Glide.with(photoImageView.getContext())
                    .load(homeData.url)
                    .into(photoImageView);
            progressDialog.dismiss();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.sign_out_menu:
                FirebaseAuth.getInstance().signOut();
                return true;
            case R.id.action_item_two:
                Intent i = new Intent(HaveHome.this, ChatList.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void submit(View view) {


        //    ET1 = (EditText) findViewById(R.id.state);
        //  ET2 = (EditText) findViewById(R.id.city);
        ET3 = (EditText) findViewById(R.id.area);
        ET4 = (EditText) findViewById(R.id.plot);
        ET5 = (EditText) findViewById(R.id.price);
        ET6 = (EditText) findViewById(R.id.agreement);
        ET7 = (EditText) findViewById(R.id.bedrooms);
        ET8 = (EditText) findViewById(R.id.bathrooms);
        ET9 = (EditText) findViewById(R.id.toilets);
//        ET10 = (EditText) findViewById(R.id.wardrobe);
//        ET11 = (EditText) findViewById(R.id.beds);
//        ET12 = (EditText) findViewById(R.id.fans);
//        ET13 = (EditText) findViewById(R.id.lights);
//        ET14 = (EditText) findViewById(R.id.modularkitchen);
//        ET15 = (EditText) findViewById(R.id.ac);
//        ET16 = (EditText) findViewById(R.id.inverter);
        ET17 = (EditText) findViewById(R.id.phoneNo);
        CB10 = (CheckBox) findViewById(R.id.wardrobe);
        CB11 = (CheckBox) findViewById(R.id.beds);
        CB12 = (CheckBox) findViewById(R.id.fans);
        CB13 = (CheckBox) findViewById(R.id.lights);
        CB14 = (CheckBox) findViewById(R.id.modularkitchen);
        CB15 = (CheckBox) findViewById(R.id.ac);
        CB16 = (CheckBox) findViewById(R.id.inverter);
        RG = (RadioGroup) findViewById(R.id.rentto);
        S1 = stateView.getText().toString();
        S2 = textView.getText().toString();
        S3 = ET3.getText().toString();
        S4 = ET4.getText().toString();
        S5 = ET5.getText().toString();
        S6 = ET6.getText().toString();
        S7 = ET7.getText().toString();
        S8 = ET8.getText().toString();
        S9 = ET9.getText().toString();
//        S10 = ET10.getText().toString();
//        S11 = ET11.getText().toString();
//        S12 = ET12.getText().toString();
//        S13 = ET13.getText().toString();
//        S14 = ET14.getText().toString();
//        S15 = ET15.getText().toString();
//        S16 = ET16.getText().toString();
        S17 = ET17.getText().toString();
        if (CB10.isChecked())
            S10 = "Yes";
        else
            S10 = "No";
        if (CB11.isChecked())
            S11 = "Yes";
        else
            S11 = "No";
        if (CB12.isChecked())
            S12 = "Yes";
        else
            S12 = "No";
        if (CB13.isChecked())
            S13 = "Yes";
        else
            S13 = "No";
        if (CB14.isChecked())
            S14 = "Yes";
        else
            S14 = "No";
        if (CB15.isChecked())
            S15 = "Yes";
        else
            S15 = "No";
        if (CB16.isChecked())
            S16 = "Yes";
        else
            S16 = "No";
        int selectedId = RG.getCheckedRadioButtonId();
        if (selectedId == R.id.singlemen) {
            S18 = "Single Men";
        } else if (selectedId == R.id.singlewomen) {
            S18 = "Single Women";
        } else if (selectedId == R.id.family) {
            S18 = "Family";
        } else {
            S18 = "";
        }


        if (S1.equals("") || S2.equals("") || S3.equals("") || S4.equals("") || S5.equals("") || S6.equals("") || S7.equals("") || S8.equals("") || S9.equals("") || S10.equals("") || S11.equals("") || S12.equals("") || S13.equals("") || S14.equals("") || S15.equals("") || S16.equals("") || S17.equals(""))
            Toast.makeText(this, "All the Fields are Required", Toast.LENGTH_LONG).show();
        else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String Owner = user.getDisplayName();
            String UID = user.getUid();
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c);
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("States");
            mStates = mMessagesDatabaseReference.child(S1);
            mCity = mStates.child(S2);
            mArea = mCity.child(S3);
            mPlot = mArea.child(S4);
            mPlot.child("Price").setValue(S5);
            mPlot.child("Agreement").setValue(S6);
            mPlot.child("BedRooms").setValue(S7);
            mPlot.child("BathRooms").setValue(S8);
            mPlot.child("Toilets").setValue(S9);
            mPlot.child("WardRobe").setValue(S10);
            mPlot.child("Beds").setValue(S11);
            mPlot.child("Fans").setValue(S12);
            mPlot.child("Lights").setValue(S13);
            mPlot.child("ModularKitchen").setValue(S14);
            mPlot.child("AC").setValue(S15);
            mPlot.child("Inverter").setValue(S16);
            mPlot.child("PhoneNo").setValue(S17);
            mPlot.child("RentTo").setValue(S18);
            mPlot.child("Owner").setValue(Owner);
            mPlot.child("Date").setValue(formattedDate);
            mPlot.child("lat").setValue(plotlat);
            mPlot.child("lng").setValue(plotlng);
            mPlot.child("UID").setValue(UID);
            //   ET1.setText("");
            // ET2.setText("");
            ET3.setText("");
            ET4.setText("");
            ET5.setText("");
            ET6.setText("");
            ET7.setText("");
            ET8.setText("");
            ET9.setText("");
            CB10.setChecked(false);
            CB11.setChecked(false);
            CB12.setChecked(false);
            CB13.setChecked(false);
            CB14.setChecked(false);
            CB15.setChecked(false);
            CB16.setChecked(false);
            ET17.setText("");
            RG.clearCheck();
            uploadImage(imageView);
            user = FirebaseAuth.getInstance().getCurrentUser();
            Owner = user.getUid();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("Users").child(Owner).child("Uploads").child(S1 + "_" + S2 + "_" + S3 + "_" + S4);
            mMessagesDatabaseReference.setValue("0");
            Toast.makeText(this, "Home Uploaded Successfully", Toast.LENGTH_LONG).show();


        }
    }

    public void chooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                String s = (String) place.getAddress();
                //String t=place.getLocale().toString();
                //Toast.makeText(this,t,Toast.LENGTH_LONG).show();
                add = s.split(",");
                //  ET1 = (EditText) findViewById(R.id.state);
                //  final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.city);
                //final AutoCompleteTextView stateView = (AutoCompleteTextView) findViewById(R.id.state);
                ET3 = (EditText) findViewById(R.id.area);
                s = add[add.length - 2].replaceAll("[0-9]", "");
                // ET1.setText(s, TextView.BufferType.EDITABLE);
                stateView.setText(s, TextView.BufferType.EDITABLE);
                s = add[add.length - 3].replaceAll("[0-9]", "");
                textView.setText(s, TextView.BufferType.EDITABLE);
                ET3.setText(add[0].toString(), TextView.BufferType.EDITABLE);

            }
        } else if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            filePath = data.getData();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);


        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);

            // TODO: Handle the error.
            // Log.i(TAG, status.getStatusMessage());

        } else if (resultCode == RESULT_CANCELED) {
            // The user canceled the operation.
        }

    }

    public void uploadImage(View view) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("States/" + S1 + "/" + S2 + "/" + S3 + "/" + S4 + "/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            mPlot.child("url").setValue(url);
                            Toast.makeText(HaveHome.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(HaveHome.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    public void captureImage(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST);
        }
    }

    public void add(double plotlat, double plotlng) {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());


            addresses = geocoder.getFromLocation(plotlat, plotlng, 1);
            if (addresses != null) {
                // String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                //String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                String subLocality = addresses.get(0).getSubLocality();
                ET3 = (EditText) findViewById(R.id.area);
                textView = (AutoCompleteTextView) findViewById(R.id.city);
                stateView = (AutoCompleteTextView) findViewById(R.id.state);
                ET3.setText(subLocality);
                textView.setText(city);
                stateView.setText(state);
                ET4 = (EditText) findViewById(R.id.plot);
                ET4.setText(knownName);
                progressDialog.dismiss();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            super.onBackPressed();
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    @Override
    protected void onStart() {
        backButtonCount = 0;
        super.onStart();
    }
}
