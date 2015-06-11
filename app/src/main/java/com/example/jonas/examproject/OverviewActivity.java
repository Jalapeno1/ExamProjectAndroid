package com.example.jonas.examproject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class OverviewActivity extends ListActivity {

    private ListView noteView;
    private Button buttonNewNote;

    private ArrayList<NoteObject> allNotes = new ArrayList<>();
    private Runnable viewNotes;
    private CustomListAdapter adapter;

    private static final String TAG = "OverviewActivity";
    public NoteObject objectToEdit;
    private static boolean DEVELOPLER_MODE = true; //Clear DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        //Reads all notes in DB
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        if(DEVELOPLER_MODE){
            dbHandler.deleteAll();
            addTestData(dbHandler);
            //ensures that DB is only reset on app startup
            DEVELOPLER_MODE = false;
        }
        allNotes = dbHandler.getAll();
        Collections.reverse(allNotes);

        for(NoteObject no : allNotes){
            //Prints to LOG
            String log = "Title: " + no.getTitle() + ", Content: " + no.getContent();
        }

        initUI();
        initButtonListener();
        initAdapter();
        initListViewListener();

    }
    
    public void initListViewListener(){
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onClickItem EXECUTED");

                objectToEdit = (NoteObject)parent.getAdapter().getItem(position);

                Log.d(TAG, objectToEdit.toString());

                String SELECTED_TITLE = objectToEdit.getTitle();
                String SELECTED_CONTENT = objectToEdit.getContent();

                //Checks if note contains path to picture and goes to ViewPictureActivity
                if(SELECTED_CONTENT.contains("/storage/")){
                    Intent i = new Intent(getApplicationContext(), ViewPictureActivity.class);
                    i.putExtra("TitleToEdit", SELECTED_TITLE);
                    i.putExtra("ContentToEdit", SELECTED_CONTENT);
                    i.putExtra("objectToChange", objectToEdit);
                    startActivity(i);
                }

                else{
                    Intent i = new Intent(getApplicationContext(), EditNoteActivity.class);
                    i.putExtra("TitleToEdit", SELECTED_TITLE);
                    i.putExtra("ContentToEdit", SELECTED_CONTENT);
                    i.putExtra("objectToChange", objectToEdit);
                    startActivity(i);
                }

            }
        });
    }

    public void initUI(){
        //noteView = (ListView) findViewById(R.id.);
        buttonNewNote = (Button) findViewById(R.id.buttonNewNote);
        Log.d(TAG, "initiated UI");
    }

    public void initButtonListener(){
        buttonNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        Log.d(TAG, "initiated ButtonListener");
    }

    public void initAdapter(){

        //instantiate the custom adapter (XML layout file; list_notes & objects; ArrayList<NoteObjects> added)
        adapter = new CustomListAdapter(this, R.layout.list_notes, allNotes);
        setListAdapter(adapter);

        //start thread for list creating
//        viewNotes = new Runnable() {
//            @Override
//            public void run() {
//                handler.sendEmptyMessage(0);
//            }
//        };

        Thread thread = new Thread(null, viewNotes, "newThread");
        thread.start();
        Log.d(TAG, "initiated Adapter");
    }

//    //handles the data used for the listView
//    private Handler handler = new Handler()
//    {
//        public void handleMessage(Message msg)
//        {
//            //NOTE: hvis der skal hentes data fra DB/server, saa goeres det her.
//            adapter = new CustomListAdapter(OverviewActivity.this, R.layout.list_notes, allNotes);
//            setListAdapter(adapter);
//            Log.d(TAG, "HandlerSet");
//        }
//    };

    public void addTestData(MyDBHandler dbHandler){
        NoteObject a,b,c,d,e,f,g,h,i,j;

        a = new NoteObject("Android", "Android is a mobile operating system (OS) based on the Linux kernel " +
                "and currently developed by Google. With a user interface based on direct manipulation, " +
                "Android is designed primarily for touchscreen mobile devices such as smartphones and " +
                "tablet computers, with specialized user interfaces for televisions (Android TV), cars " +
                "(Android Auto), and wrist watches (Android Wear). The OS uses touch inputs that loosely " +
                "correspond to real-world actions, like swiping, tapping, pinching, and reverse pinching " +
                "to manipulate on-screen objects, and a virtual keyboard. Despite being primarily designed " +
                "for touchscreen input, it has also been used in game consoles, digital cameras, regular PCs, " +
                "and other electronics. As of 2015, Android has the largest installed base of all " +
                "general-purpose operating systems.");
        b = new NoteObject("Google", "Google is an American multinational technology company specializing " +
                "in Internet-related services and products. These include online advertising technologies, " +
                "search, cloud computing, and software. Most of its profits are derived from " +
                "AdWords,an online advertising service that places advertising near the list of " +
                "search results.");
        c = new NoteObject("Java (programming language)", "Java is a general-purpose computer programming " +
                "language that is concurrent, class-based, object-oriented, and specifically designed " +
                "to have as few implementation dependencies as possible. It is intended to let application " +
                "developers \"write once, run anywhere\" (WORA), meaning that compiled Java code can " +
                "run on all platforms that support Java without the need for recompilation. Java " +
                "applications are typically compiled to bytecode that can run on any Java virtual machine " +
                "(JVM) regardless of computer architecture. As of 2015, Java is one of the most popular " +
                "programming languages in use, particularly for client-server web applications, " +
                "with a reported 9 million developers.[citation needed] Java was originally developed by " +
                "James Gosling at Sun Microsystems (which has since been acquired by Oracle Corporation) " +
                "and released in 1995 as a core component of Sun Microsystems' Java platform. The language " +
                "derives much of its syntax from C and C++, but it has fewer low-level facilities than " +
                "either of them.");
        d = new NoteObject("Microsoft", "Microsoft Corporation is an American " +
                "multinational technology company headquartered in Redmond, Washington, " +
                "that develops, manufactures, licenses, supports and sells computer software, " +
                "consumer electronics and personal computers and services. Its best known software " +
                "products are the Microsoft Windows line of operating systems, Microsoft Office office " +
                "suite, and Internet Explorer web browser. Its flagship hardware products are the " +
                "Xbox game consoles and the Microsoft Surface tablet lineup. It is the world's " +
                "largest software maker measured by revenues. It is also one of the world's most " +
                "valuable companies.");
        e = new NoteObject("Internet Explorer", "Internet Explorer is one of the most widely used web " +
                "browsers, attaining a peak of about 95% usage share during 2002 and 2003. Its " +
                "usage share has since declined with the launch of Firefox (2004) and Google Chrome " +
                "(2008), and with the growing popularity of operating systems such as OS X, Linux, " +
                "iOS and Android that do not run Internet Explorer. Estimates for Internet Explorer's " +
                "overall market share range from 16.9% to 57.38% (or even as low as 13.09% when counting " +
                "all platforms), as of February 2015 (browser market share is notoriously " +
                "difficult to calculate). Microsoft spent over US$100 million per year on Internet" +
                " Explorer in the late 1990s, with over 1000 people working on it by 1999.");
        f = new NoteObject("Waterfall model", "The waterfall model is a sequential design process, " +
                "used in software development processes, in which progress is seen as flowing steadily " +
                "downwards (like a waterfall) through the phases of conception, initiation, analysis, " +
                "design, construction, testing, production/implementation and maintenance.");
        g = new NoteObject("Agile software development", "Agile software development is a group of software development " +
                "methods in which requirements and solutions evolve through collaboration between " +
                "self-organizing, cross-functional teams. It promotes adaptive planning, evolutionary " +
                "development, early delivery, continuous improvement, and encourages rapid and " +
                "flexible response to change.");
        h = new NoteObject("Unified Process", "The Unified Process is not simply a process, but " +
                "rather an extensible framework which should be customized for specific organizations " +
                "or projects. The Rational Unified Process is, similarly, a customizable framework. " +
                "As a result it is often impossible to say whether a refinement of the process was " +
                "derived from UP or from RUP, and so the names tend to be used interchangeably.\n" +
                "\n" + "The name Unified Process as opposed to Rational Unified Process is generally " +
                "used to describe the generic process, including those elements which are common to " +
                "most refinements. The Unified Process name is also used to avoid potential issues of " +
                "trademark infringement since Rational Unified Process and RUP are trademarks of IBM. " +
                "The first book to describe the process was titled The Unified Software Development " +
                "Process (ISBN 0-201-57169-2) and published in 1999 by Ivar Jacobson, Grady Booch " +
                "and James Rumbaugh. Since then various authors unaffiliated with Rational Software " +
                "have published books and articles using the name Unified Process, whereas authors " +
                "affiliated with Rational Software have favored the name Rational Unified Process.");
        i = new NoteObject("Scrum (software development)", "Scrum is an iterative and incremental " +
                "agile software development methodology for managing product development. " +
                "It defines \"a flexible, holistic product development strategy where a development " +
                "team works as a unit to reach a common goal\", challenges assumptions of " +
                "the \"traditional, sequential approach\" to product development, and enables " +
                "teams to self-organize by encouraging physical co-location or close online " +
                "collaboration of all team members, as well as daily face-to-face communication " +
                "among all team members and disciplines in the project.");
        j = new NoteObject("Extreme programming", "Extreme programming (XP) is a software development " +
                "methodology which is intended to improve software quality and responsiveness to " +
                "changing customer requirements. As a type of agile software development, " +
                "it advocates frequent \"releases\" in short development cycles, which is " +
                "intended to improve productivity and introduce checkpoints at which new customer " +
                "requirements can be adopted.\n" +
                "\n" +
                "Other elements of extreme programming include: programming in pairs " +
                "or doing extensive code review, unit testing of all code, avoiding programming " +
                "of features until they are actually needed, a flat management structure, simplicity " +
                "and clarity in code, expecting changes in the customer's requirements as time " +
                "passes and the problem is better understood, and frequent communication with " +
                "the customer and among programmers.The methodology takes its name from the " +
                "idea that the beneficial elements of traditional software engineering practices " +
                "are taken to \"extreme\" levels. As an example, code reviews are considered a " +
                "beneficial practice; taken to the extreme, code can be reviewed continuously, " +
                "i.e. the practice of pair programming.");

        dbHandler.addNote(a);
        dbHandler.addNote(b);
        dbHandler.addNote(c);
        dbHandler.addNote(d);
        dbHandler.addNote(e);
        dbHandler.addNote(f);
        dbHandler.addNote(g);
        dbHandler.addNote(h);
        dbHandler.addNote(i);
        dbHandler.addNote(j);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
