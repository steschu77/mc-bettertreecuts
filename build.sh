mkdir .build
javac -cp $1 -d .build com/steschu/BetterTreeCuts/BetterTreeCuts.java
cp plugin.yml .build/
cd .build
jar -cvf BetterTreeCuts.jar com/steschu/BetterTreeCuts/BetterTreeCuts.class plugin.yml
# cp BetterTreeCuts.jar ~/mc/server/plugins/
# cd ..
