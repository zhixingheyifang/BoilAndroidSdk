package com.example.nuonuo.providerdemo;

import android.util.Log;
import android.view.MenuItem;

import java.util.logging.Handler;

public class MainActivity extends BaseActivity
implements IReportBack
{
	public static final String tag="MainActivity";
	private ProviderTester providerTester = null;

	public MainActivity()
	{

		super(R.menu.main_menu,tag);
		this.retainState();
		providerTester = new ProviderTester(this,this);
	}

    protected boolean onMenuItemSelected(MenuItem item)
    {
    	Log.d(tag,item.getTitle().toString());
    	if (item.getItemId() == R.id.menu_add)
    	{
    		providerTester.addBook();
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_display_table)
    	{
    		providerTester.showBooks();
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_delete)
    	{
    		providerTester.removeBook();
    		return true;
    	}
    	return true;
    }
}