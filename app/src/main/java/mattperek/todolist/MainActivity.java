package mattperek.todolist;

import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    ArrayList<String> listItems;
    ArrayAdapter<String> listAdapter;
    ListView itemList;

    public void onAddItem(View view){
        EditText newItemText =(EditText) findViewById(R.id.newItemText);
        String itemText = newItemText.getText().toString();
        listAdapter.add(itemText);
        newItemText.setText("");
        writeItems();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemList= (ListView) findViewById(R.id.itemList);
        listItems= new ArrayList<String>();
        readItems();
        listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        itemList.setAdapter(listAdapter);
        listItems.add(0,"First Item");
        listItems.add(1,"Second Item");
        setUpViewListener();
    }

    private void setUpViewListener(){
       itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               listItems.remove(position);
               listAdapter.notifyDataSetChanged();
               writeItems();
               return true;
           }
       });
    }
    private void readItems()
    {
        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "toDoList.txt");
        try{
            listItems = new ArrayList<>(FileUtils.readLines(toDoFile));
        }
        catch(IOException e){
            listItems= new ArrayList<>();
        }
    }
    private void writeItems()
    {
        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "toDoList.txt");
        try{
            FileUtils.writeLines(toDoFile,listItems);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void changeBackgroundColor()
    {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
