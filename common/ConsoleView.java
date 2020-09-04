package common;

import interfaces.IModel;
import interfaces.IView;

public class ConsoleView implements IView {
    private IModel model;
    

    public ConsoleView(IModel model) {
       this.model = model;
    }

    @Override
    public void refresh() {
        System.out.println(model);
    }

}
