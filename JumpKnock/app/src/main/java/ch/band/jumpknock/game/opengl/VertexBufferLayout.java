package ch.band.jumpknock.game.opengl;

import java.util.ArrayList;
import java.util.List;

class VertexBufferLayout {
    public int getStride() {
        return stride;
    }

    class VertexBufferElement {
        public int count;
        public Util.GlType type;
        public boolean normalized;
        public VertexBufferElement(int count, Util.GlType type, boolean normalized){
            this.count = count;
            this.type = type;
            this.normalized = normalized;
        }
    }

    private ArrayList<VertexBufferElement> elements;
    private int stride;

    public  void Push(int count,Class type) {
        Util.GlType glType = Util.GetGlType(type);
        elements.add(new VertexBufferElement(count,glType,false));
        stride += glType.BYTES * count;
    }

    public List<VertexBufferElement> getElements(){
        return  elements;
    }
}
