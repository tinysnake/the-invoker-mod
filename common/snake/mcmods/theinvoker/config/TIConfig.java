package snake.mcmods.theinvoker.config;

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class TIConfig extends Configuration
{
    public final static String CATEGORY_OTHERS = "others";
    public final static String KEY_VERSION = "version";
    
    public TIConfig(File file)
    {
        super(file);
    }
    
    public Property getVersionProp()
    {
        return get(CATEGORY_OTHERS, KEY_VERSION, "");
    }
    
    public String getVersion()
    {
        return getVersionProp().getString();
    }
    
    public void setVersion(String value)
    {
        getVersionProp().set(value);
    }
}
