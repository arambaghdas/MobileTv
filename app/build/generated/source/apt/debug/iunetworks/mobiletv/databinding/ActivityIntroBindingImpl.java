package iunetworks.mobiletv.databinding;
import iunetworks.mobiletv.R;
import iunetworks.mobiletv.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityIntroBindingImpl extends ActivityIntroBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.support.constraint.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    private OnClickListenerImpl mPresenterHandleSignUpClickAndroidViewViewOnClickListener;
    private OnClickListenerImpl1 mPresenterHandleSignInClickAndroidViewViewOnClickListener;
    // Inverse Binding Event Handlers

    public ActivityIntroBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }
    private ActivityIntroBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[1]
            , (android.widget.Button) bindings[2]
            );
        this.mboundView0 = (android.support.constraint.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.signInButton.setTag(null);
        this.signUpButton.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.presenter == variableId) {
            setPresenter((iunetworks.mobiletv.presenter.IntroPresenter) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setPresenter(@Nullable iunetworks.mobiletv.presenter.IntroPresenter Presenter) {
        this.mPresenter = Presenter;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.presenter);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        iunetworks.mobiletv.presenter.IntroPresenter presenter = mPresenter;
        android.view.View.OnClickListener presenterHandleSignUpClickAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener presenterHandleSignInClickAndroidViewViewOnClickListener = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (presenter != null) {
                    // read presenter::handleSignUpClick
                    presenterHandleSignUpClickAndroidViewViewOnClickListener = (((mPresenterHandleSignUpClickAndroidViewViewOnClickListener == null) ? (mPresenterHandleSignUpClickAndroidViewViewOnClickListener = new OnClickListenerImpl()) : mPresenterHandleSignUpClickAndroidViewViewOnClickListener).setValue(presenter));
                    // read presenter::handleSignInClick
                    presenterHandleSignInClickAndroidViewViewOnClickListener = (((mPresenterHandleSignInClickAndroidViewViewOnClickListener == null) ? (mPresenterHandleSignInClickAndroidViewViewOnClickListener = new OnClickListenerImpl1()) : mPresenterHandleSignInClickAndroidViewViewOnClickListener).setValue(presenter));
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            this.signInButton.setOnClickListener(presenterHandleSignInClickAndroidViewViewOnClickListener);
            this.signUpButton.setOnClickListener(presenterHandleSignUpClickAndroidViewViewOnClickListener);
        }
    }
    // Listener Stub Implementations
    public static class OnClickListenerImpl implements android.view.View.OnClickListener{
        private iunetworks.mobiletv.presenter.IntroPresenter value;
        public OnClickListenerImpl setValue(iunetworks.mobiletv.presenter.IntroPresenter value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.handleSignUpClick(arg0); 
        }
    }
    public static class OnClickListenerImpl1 implements android.view.View.OnClickListener{
        private iunetworks.mobiletv.presenter.IntroPresenter value;
        public OnClickListenerImpl1 setValue(iunetworks.mobiletv.presenter.IntroPresenter value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.handleSignInClick(arg0); 
        }
    }
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): presenter
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}