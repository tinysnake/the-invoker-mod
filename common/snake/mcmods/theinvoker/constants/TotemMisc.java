package snake.mcmods.theinvoker.constants;


public class TotemMisc
{
    public enum TotemType
    {
        TYPE_GHOST(0),
        TYPE_SOUL(1),
        TYPE_SOUL_ATTRACTIVE(2),
        TYPE_RUNE(3),
        TYPE_MASSACRE(4);
        
        private int metadata;
        
        private TotemType(int metadata)
        {
            this.metadata = metadata;
        }
        
        public int getMetadata()
        {
            return metadata;
        }
        
        public static TotemType getType(int i)
        {
            if(i<values().length)
                return values()[i];
            return TYPE_GHOST;
        }
    }
    
    public static final int[] TOTEM_EFFECTIVE_RANGES = {
        //ghost
        0,
        //soul_attractive
        8,
        //soul
        16,
        //rune
        8,
        //massacre
        6
    };
    
    public static int getEffectiveRangeByMetadata(TotemType metadata)
    {
        return TOTEM_EFFECTIVE_RANGES[metadata.ordinal()];
    }
}
