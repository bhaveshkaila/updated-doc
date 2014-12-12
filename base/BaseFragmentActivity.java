

public class BaseFragmentActivity extends FragmentActivity {

	
	public void addFragment(Fragment fragment){
		
		ViewGroup baseView = (ViewGroup)View.inflate(this, R.layout.base_activity_layout, null);
		setContentView(baseView);
		//baseView.findViewById(R.id.baseFragmentContainer);
		
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.baseLayout, fragment);
		fragmentTransaction.commit();
		
	}
}
