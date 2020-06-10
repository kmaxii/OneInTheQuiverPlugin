package me.kmaxi.oneInTheQuiver.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class FireWork {
    public static void launchFirework(Player p){
        Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        Random r = new Random();

        int rt = r.nextInt(4) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        if (rt == 1) type = FireworkEffect.Type.BALL;
        if (rt == 2) type = FireworkEffect.Type.BALL_LARGE;
        if (rt == 3) type = FireworkEffect.Type.BURST;
        if (rt == 4) type = FireworkEffect.Type.CREEPER;
        if (rt == 5) type = FireworkEffect.Type.STAR;

        int r1i = r.nextInt(17) + 1;
        int r2i = r.nextInt(17) + 1;
        Color c1 = getColor(r1i);
        Color c2 = getColor(r2i);

        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
        fwm.addEffect(effect);

        int rp = r.nextInt(2) + 1;
        fwm.setPower(rp);

        fw.setFireworkMeta(fwm);
    }

    private static Color getColor(int i) {
        Color c = null;

        if(i==1){c=Color.AQUA;}
        else if(i==2){c=Color.BLACK;}
        else if(i==3){c=Color.BLUE;}
        else if(i==4){c=Color.FUCHSIA;}
        else if(i==5){c=Color.GRAY;}
        else if(i==6){c=Color.GREEN;}
        else if(i==7){c=Color.LIME;}
        else if(i==8){c=Color.MAROON;}
        else if(i==9){c=Color.NAVY;}
        else if(i==10){c=Color.OLIVE;}
        else if(i==11){c=Color.ORANGE;}
        else if(i==12){c=Color.PURPLE;}
        else if(i==13){c=Color.RED;}
        else if(i==14){c=Color.SILVER;}
        else if(i==15){c=Color.TEAL;}
        else if(i==16){c=Color.WHITE;}
        else{c=Color.YELLOW;}

        return c;
    }
}

