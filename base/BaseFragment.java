package com.wink.fragment.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wink.R;
import com.wink.custom.view.CustomButton;
import com.wink.custom.view.CustomTextView;
import com.wink.localstorage.UserSharedPreferences;
import com.wink.model.UserType;

public class BaseFragment extends Fragment implements OnClickListener {

	public View mRoot;
	public CustomButton btnBack, btnSave, btnLogout, btnPostFeed, btnRefresh,
			btnCreateGroup, btnAddToGroup,btnWink, btnSetting;
	public RelativeLayout layoutHeader;

    public CustomTextView txtHeaderTitle;
    public ImageView ivWinkLogo;

    public LinearLayout parentContainer;

	public View getContainerView(Context context, int contentId,
			String fragmentName) {

		mRoot = View.inflate(context, R.layout.base_fragment_layout, null);
		mRoot.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));

		LinearLayout baseFragmentLayout = (LinearLayout) mRoot
				.findViewById(R.id.layoutBaseFragment);
        parentContainer= (LinearLayout) mRoot.findViewById(R.id.parentContainer);

		ViewGroup layout = (ViewGroup) View.inflate(context, contentId,
				baseFragmentLayout);

        txtHeaderTitle= (CustomTextView) mRoot.findViewById(R.id.txtHeaderTitle);
        ivWinkLogo= (ImageView) mRoot.findViewById(R.id.imgWinkLogo);

        btnWink = (CustomButton) mRoot.findViewById(R.id.btnWink);
        btnSetting = (CustomButton) mRoot.findViewById(R.id.btnSetting);
        btnBack = (CustomButton) mRoot.findViewById(R.id.btnBack);
		btnSave = (CustomButton) mRoot.findViewById(R.id.btnSave);
		btnPostFeed = (CustomButton) mRoot.findViewById(R.id.btnPostFeed);
		btnLogout = (CustomButton) mRoot.findViewById(R.id.btnLogout);
		btnRefresh = (CustomButton) mRoot.findViewById(R.id.btnRefresh);
		layoutHeader = (RelativeLayout) mRoot.findViewById(R.id.layoutHeader);
		btnCreateGroup = (CustomButton) mRoot.findViewById(R.id.btnCreateGroup);
		btnAddToGroup = (CustomButton) mRoot.findViewById(R.id.btnAddToGroup);
		// baseFragmentLayout.addView(layout, 1);
		layout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));

		btnBack.setOnClickListener(this);
		btnSave.setOnClickListener(this);

		mRoot.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					btnBack.performClick();
				}
				return false;
			}
		});
		return mRoot;
	}

	@Override
	public void onClick(View v) {

		if (v == btnBack) {

			getActivity().onBackPressed();

		}
	}

    public void setBackGround(int color){
        parentContainer.setBackgroundColor(color);
    }

    public void showHeaderTitle(String headerTitle) {
            txtHeaderTitle.setVisibility(View.VISIBLE);
            ivWinkLogo.setVisibility(View.GONE);
            txtHeaderTitle.setText(headerTitle);
    }

    public void showWinkLogo(){
        txtHeaderTitle.setVisibility(View.GONE);
        ivWinkLogo.setVisibility(View.VISIBLE);
    }


    public void showBackButton(boolean needToShow) {
        if (needToShow) {
            btnBack.setVisibility(View.VISIBLE);
        } else {
            btnBack.setVisibility(View.INVISIBLE);
        }
    }

    public void showSettingButton(boolean needToShow) {
        if (needToShow) {
            btnSetting.setVisibility(View.VISIBLE);
        } else {
            btnSetting.setVisibility(View.INVISIBLE);
        }
    }

    public void showWinkButton(boolean needToShow) {
        if (needToShow) {
            btnWink.setVisibility(View.VISIBLE);
        } else {
            btnWink.setVisibility(View.INVISIBLE);
        }
    }

	public void showSaveButton(boolean needToShow,OnClickListener listener) {
		if (needToShow) {
			btnSave.setVisibility(View.VISIBLE);
            btnSave.setOnClickListener(listener);
		} else {
			btnSave.setVisibility(View.GONE);
		}
	}

	public void showHeaderLayout(boolean needToShow) {

		if (needToShow) {
			layoutHeader.setVisibility(View.VISIBLE);
		} else {
			layoutHeader.setVisibility(View.GONE);
		}
	}

	public void showLogoutBtn(boolean needToShow) {

		if (needToShow) {
			btnLogout.setVisibility(View.VISIBLE);
		} else {
			btnLogout.setVisibility(View.GONE);
		}
	}

	public void showPostFeedBtn(boolean needToShow) {

		if (needToShow) {
			UserType userType = UserSharedPreferences.getInstance(getActivity()).getUserType();
			if(userType != null && !userType.isGeneralUser())
				btnPostFeed.setVisibility(View.VISIBLE);
		} else {
			btnPostFeed.setVisibility(View.GONE);
		}
	}

	public void showRefreshBtn(boolean needToShow) {

		if (needToShow) {
			btnRefresh.setVisibility(View.VISIBLE);
		} else {
			btnRefresh.setVisibility(View.GONE);
		}
	}

	public void showCreateGroupButton(boolean needToShow) {
		if (needToShow)
			btnCreateGroup.setVisibility(View.VISIBLE);
		else
			btnCreateGroup.setVisibility(View.GONE);
	}

	public void showAddToGroupButton(boolean needToShow) {
		if (needToShow)
			btnAddToGroup.setVisibility(View.VISIBLE);
		else
			btnAddToGroup.setVisibility(View.GONE);
	}
}
