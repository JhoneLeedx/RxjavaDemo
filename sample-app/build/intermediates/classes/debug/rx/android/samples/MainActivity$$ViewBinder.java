// Generated code from Butter Knife. Do not modify!
package rx.android.samples;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends rx.android.samples.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492980, "field 'refreshLayout'");
    target.refreshLayout = finder.castView(view, 2131492980, "field 'refreshLayout'");
    view = finder.findRequiredView(source, 2131492981, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131492981, "field 'recyclerView'");
  }

  @Override public void unbind(T target) {
    target.refreshLayout = null;
    target.recyclerView = null;
  }
}
