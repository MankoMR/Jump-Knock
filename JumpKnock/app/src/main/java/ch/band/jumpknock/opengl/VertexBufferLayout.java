package ch.band.manko.glrenderer.opengl;

import java.util.ArrayList;
import java.util.List;

class VertexBufferLayout {

    private ArrayList<VertexBufferElement> elements;
    private int stride;

    public VertexBufferLayout(){
        this.elements = new ArrayList<>();
    }

    public  void Push(int count,Class type,String name) {
        Util.GlType glType = Util.GetGlType(type);
        elements.add(new VertexBufferElement(count,glType,name,false));
        stride += glType.BYTES * count;
    }

    public int getStride() {
        return stride;
    }
    public List<VertexBufferElement> getElements(){
        return  elements;
    }

    class VertexBufferElement {
        public int count;
        public Util.GlType type;
        public String name;
        public boolean normalized;
        public VertexBufferElement(int count, Util.GlType type, String name, boolean normalized){
            this.count = count;
            this.type = type;
            this.name = name;
            this.normalized = normalized;
        }
    }
}
