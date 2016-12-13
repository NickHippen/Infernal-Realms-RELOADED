package net.infernalrealms.blacksmithing;

import java.util.Random;

public final class BowNameGenerator {

	private static final Random RANDOM = new Random();
	private static final String[] nm1 = new String[] { "Hornet's Sting", "Blackened Sky", "Darkened Sky", "Steel Hail", "Archangel",
			"Archdemon", "Arcus", "Armadillo", "Armageddon", "Arrowsong", "Arrowspike", "Avalance", "Back Pain", "Backsnipe", "Ballista",
			"Barrage", "Beesting", "Betrayal", "Betrayal's Sting", "Bolt", "Bolt Action", "Bon Voyage", "Bristleblitz", "Bullseye",
			"Calamity", "Chimera", "Clutch", "Comet", "Courier", "Crier", "Curvey", "Cyclone", "Dash", "Dead Air", "Death's Kiss",
			"Death's Sigh", "Death's Whisper", "Decimate", "Deliverance", "Deluge", "Destiny's Song", "Devil's Kiss", "Devil's Recurve",
			"Devil's Sting", "Devil's Whisper", "Doomcaster", "Drawback", "Drawling", "Drawstring", "Dream Catcher", "Eagle",
			"Eagle Strike", "Emily's Curve", "Euthanasia", "Eye of Eternity", "Eye of Fidelity", "Eye of Precision", "Eye of Truth",
			"Falling Star", "Featherdraw", "Final Breath", "Final Voyage", "Final Whisper", "Final Whistle", "Firestarter", "Fling", "Flux",
			"Fury", "Gargoyle", "Graviton", "Hailstorm", "Hamstring", "Hamstrung", "Harbinger", "Hatred's Sting", "Hawkeye", "Heartbeat",
			"Heartpiercer", "Heartstriker", "Heartstring", "Hedgehog", "Hell's Whistle", "High-Strung", "Hooty Tooty Aim and Shooty",
			"Hope's End", "Hornet", "Huntress", "Hurricane", "Hush", "Imminent Doom", "Impending Doom", "Irk", "Jugular", "Kiss of Death",
			"Lash", "Last Kiss", "Last Vision", "Last Whisper", "Lightning", "Lightning Strike", "Long Shot", "Messenger", "Meteor",
			"Meteor Strike", "Midge", "Mirage", "Misery's End", "Molten Fury", "Mosquito", "Needle Shooter", "Needle Threader",
			"Netherstrand", "Penetrator", "Perfidy", "Phantom", "Phantom Strike", "Phoenix", "Pierce", "Pinch", "Pique", "Pluck",
			"Porcupine", "Precise", "Precision", "Prickle", "Prophet", "Puncture", "Quickstrike", "Quillshooter", "Quintain", "Rain Maker",
			"Razorsong", "Razorwind", "Recurve", "Rigormortis", "Salvation", "Scorpio", "Scorpion", "Scorpion Sting", "Shadow Strike",
			"Shadow's Bane", "Shadow's Strike", "Shatter Storm", "Shooting Star", "Shriek", "Silent Messenger", "Silentsong",
			"Siren's Call", "Siren's Cry", "Siren's Song", "Sky Piercer", "Skyfire", "Snatch", "Snipe", "Snitch", "Soulstring",
			"Special Delivery", "Spitfire", "Splinter", "Splintermark", "Squawk", "Squirm", "Star's Fury", "Starshot", "Starstruck",
			"Sting", "Stormsong", "Striker's Mark", "Stryker", "Sudden Death", "Surprise", "Swiftwind", "Swoosh", "Talonstrike", "Tempest",
			"The Ambassador", "The Messenger", "Thunder", "Thunderstrike", "Tiebreaker", "Tranquility", "Trophy Chord", "Trophy Gatherer",
			"Trophy Mark", "Truestrike", "Tweak", "Twisted", "Twister", "Twitch", "Typhoon", "Valkyrie", "Vehement Chord", "Venomstrike",
			"Viper", "Vixen", "Vulture", "Warsong", "Wasp", "Wasp's Sting", "Whelm", "Whisper", "Whisperwind", "Windbreaker", "Windforce",
			"Windlass", "Windrunner", "Windstalker", "Windtalker", "WithDraw" };
	private static final String[] nm2 = new String[] { "Advanced", "Amber Infused", "Ancient", "Anguish'", "Annihilation", "Antique",
			"Arcane", "Arched", "Archer's", "Assassination", "Atuned", "Bandit's", "Baneful", "Banished", "Barbarian", "Barbaric",
			"Battleworn", "Blazefury", "Blessed", "Blood Infused", "Bloodcursed", "Bloodied", "Bloodlord's", "Bloodsurge", "Bloodvenom",
			"Bone Crushing", "Bowman's", "Brutal", "Brutality", "Burnished", "Cataclysm", "Cataclysmic", "Charmed", "Corrupted", "Crazed",
			"Crying", "Cursed", "Curved", "Dancing", "Defiled", "Deluded", "Demonic", "Deserted", "Desire's", "Desolation", "Destiny's",
			"Dire", "Doom", "Doom's", "Dragon's", "Dragonbreath", "Dreadful", "Ebon", "Eerie", "Enchanted", "Engraved", "Eternal",
			"Ethereal", "Executing", "Exiled", "Expert's", "Extinction", "Faith's", "Faithful", "Fancy", "Fearful", "Feather-Wrapped",
			"Featherdraw", "Feral", "Fierce", "Fiery", "Fine", "Firestorm", "Flimsy", "Forsaken", "Fortune's", "Fragile", "Frail",
			"Frenzied", "Frost", "Frozen", "Furious", "Fusion", "Ghastly", "Ghostly", "Gladiator", "Gladiator's", "Grieving", "Guard's",
			"Guardian's", "Hateful", "Haunted", "Heavy", "Hollow", "Holy", "Honed", "Honor's", "Hope's", "Hopeless", "Howling", "Hungering",
			"Improved", "Incarnated", "Infused", "Inherited", "Isolated", "Jade Infused", "Judgement", "Liar's", "Lich", "Lightning",
			"Lonely", "Loyal", "Lustful", "Lusting", "Mage's", "Malevolent", "Malicious", "Malignant", "Massive", "Master Hunter's",
			"Mended", "Mercenary", "Military", "Misfortune's", "Mourning", "Nightmare", "Nightstalker", "Ominous", "Peacekeeper", "Phantom",
			"Possessed", "Pride's", "Prideful", "Primitive", "Promised", "Protector's", "Proud", "Ranger's", "Recruit's", "Reincarnated",
			"Relentless", "Remorseful", "Renewed", "Renovated", "Replica", "Restored", "Retribution", "Ritual", "Roaring", "Savage",
			"Oathkeeper's", "Shadow", "Shadowleaf", "Shrieking", "Silent", "Singed", "Singing", "Sinister", "Skullforge", "Skyfall",
			"Smooth", "Solitude's", "Sorrow's", "Soul", "Soul Infused", "Soulcursed", "Soulless", "Spectral", "Spiteful", "Storm",
			"Storm's", "Stormfury", "Stormguard", "Stout", "Striker's", "Sturdy", "Terror", "Thirsting", "Thirsty", "Thunder",
			"Thunderfury", "Thunderguard", "Thundersoul", "Timeworn", "Tormented", "Tracking", "Trainee's", "Trapper's", "Treachery's",
			"Twilight", "Twilight's", "Twisted", "Tyrannical", "Undead", "Unholy", "Vengeance", "Vengeful", "Venom", "Vicious",
			"Vindication", "Vindictive", "Void", "Volcanic", "Vowed", "War-Forged", "Warlord's", "Warp", "Warped", "Warsong",
			"Well Crafted", "Whistling", "Wicked", "Wind's", "Windsong", "Woeful", "Wrathful", "Wretched", "Yearning", "Yielding",
			"Zealous" };
	private static final String[] nm3 = new String[] { "Ashwood", "Bone", "Bronzed", "Driftwood", "Ebon", "Hardwood", "Iron", "Ironbark",
			"Maple", "Oak", "Redwood", "Skeletal", "Steel", "Titanium", "Warpwood", "Willow", "Yew" };
	private static final String[] nm4 = new String[] { "Longbow", "Shortbow", "Crossbow", "Speargun", "Launcher", "Repeater", "Shooter",
			"Crossfire", "Bolter", "Heavy Crossbow", "Arbalest", "Piercer", "Striker", "Warbow", "Chord", "Recurve", "Bow", "Compound Bow",
			"Hunting Bow", "Warp-Bow", "Flatbow", "Reflex Bow", "Composite Bow", "Compound Crossbow", "Straight Bow", "Self Bow" };
	private static final String[] nm5 = new String[] { "Annihilation", "Arbalest", "Arch", "Betrayer", "Bite", "Bolter", "Bond", "Boon",
			"Bow", "Breaker", "Bringer", "Call", "Champion", "Chord", "Composite Bow", "Compound Bow", "Crescent", "Crook", "Crossbow",
			"Crossfire", "Cry", "Cunning", "Curve", "Dawn", "Defiler", "Destroyer", "Eclipse", "Ellipse", "Ender", "Etcher", "Executioner",
			"Eye", "Favor", "Ferocity", "Flatbow", "Foe", "Gift", "Glory", "Guardian", "Heavy Crossbow", "Heirloom", "Hope", "Hunting Bow",
			"Incarnation", "Kiss", "Last Hope", "Last Stand", "Launcher", "Legacy", "Longbow", "Memory", "Might", "Oath", "Pact", "Piercer",
			"Pique", "Pledge", "Poke", "Prick", "Promise", "Protector", "Ravager", "Reach", "Recurve", "Reflex Bow", "Repeater", "Savagery",
			"Secret", "Self Bow", "Shooter", "Shortbow", "Skewer", "Soul", "Speargun", "Spike", "Spine", "Straight Bow", "Striker",
			"String", "Terror", "Token", "Tribute", "Vengeance", "Voice", "Warbow", "Warp-Bow", "Whisper", "Wit" };
	private static final String[] nm6 = new String[] { "of Agony", "of Ancient Power", "of Anguish", "of Ashes", "of Assassins",
			"of Black Magic", "of Blessed Fortune", "of Blessings", "of Blight", "of Blood", "of Bloodlust", "of Broken Bones",
			"of Broken Dreams", "of Broken Families", "of Burdens", "of Chaos", "of Closing Eyes", "of Conquered Worlds", "of Corruption",
			"of Cruelty", "of Cunning", "of Dark Magic", "of Dark Souls", "of Darkness", "of Decay", "of Deception", "of Degradation",
			"of Delusions", "of Denial", "of Desecration", "of Diligence", "of Dismay", "of Dragonsouls", "of Due Diligence", "of Echoes",
			"of Ended Dreams", "of Ending Hope", "of Ending Misery", "of Eternal Bloodlust", "of Eternal Damnation", "of Eternal Glory",
			"of Eternal Justice", "of Eternal Rest", "of Eternal Sorrow", "of Eternal Struggles", "of Eternity", "of Executions",
			"of Faded Memories", "of Fallen Souls", "of Fools", "of Frost", "of Frozen Hells", "of Fury", "of Giants", "of Giantslaying",
			"of Grace", "of Grieving Widows", "of Hate", "of Hatred", "of Hell's Games", "of Hellish Torment", "of Heroes", "of Holy Might",
			"of Honor", "of Hope", "of Horrid Dreams", "of Horrors", "of Illuminated Dreams", "of Illumination", "of Immortality",
			"of Inception", "of Infinite Trials", "of Insanity", "of Invocation", "of Justice", "of Light's Hope", "of Lost Comrades",
			"of Lost Hope", "of Lost Voices", "of Lost Worlds", "of Magic", "of Mercy", "of Misery", "of Mountains", "of Mourning",
			"of Mystery", "of Necromancy", "of Nightmares", "of Oblivion", "of Perdition", "of Phantoms", "of Power", "of Pride",
			"of Pride's Fall", "of Putrefaction", "of Reckoning", "of Redemption", "of Regret", "of Riddles", "of Secrecy", "of Secrets",
			"of Shadow Strikes", "of Shadows", "of Shifting Sands", "of Shifting Worlds", "of Silence", "of Slaughter", "of Souls",
			"of Stealth", "of Storms", "of Subtlety", "of Suffering", "of Suffering's End", "of Summoning", "of Terror", "of Thunder",
			"of Time-Lost Memories", "of Timeless Battles", "of Titans", "of Torment", "of Traitors", "of Trembling Hands", "of Trials",
			"of Truth", "of Twilight's End", "of Twisted Visions", "of Unholy Blight", "of Unholy Might", "of Vengeance", "of Visions",
			"of Wasted Time", "of Widows", "of Wizardry", "of Woe", "of Wraiths", "of Zeal", "of the Ancients", "of the Banished",
			"of the Basilisk", "of the Beast", "of the Blessed", "of the Breaking Storm", "of the Brotherhood", "of the Burning Sun",
			"of the Caged Mind", "of the Cataclysm", "of the Champion", "of the Claw", "of the Corrupted", "of the Covenant",
			"of the Crown", "of the Damned", "of the Daywalker", "of the Dead", "of the Depth", "of the Dreadlord", "of the Earth",
			"of the East", "of the Emperor", "of the Empty Void", "of the End", "of the Enigma", "of the Fallen", "of the Falling Sky",
			"of the Flame", "of the Forest", "of the Forgotten", "of the Forsaken", "of the Gladiator", "of the Harvest", "of the Immortal",
			"of the Incoming Storm", "of the Insane", "of the King", "of the Lasting Night", "of the Leviathan", "of the Light",
			"of the Lion", "of the Lionheart", "of the Lone Victor", "of the Lone Wolf", "of the Lost", "of the Moon", "of the Moonwalker",
			"of the Night Sky", "of the Night", "of the Nightstalker", "of the North", "of the Occult", "of the Oracle", "of the Phoenix",
			"of the Plague", "of the Prince", "of the Protector", "of the Queen", "of the Serpent", "of the Setting Sun", "of the Shadows",
			"of the Sky", "of the South", "of the Stars", "of the Storm", "of the Summoner", "of the Sun", "of the Sunwalker",
			"of the Talon", "of the Undying", "of the Victor", "of the Void", "of the West", "of the Whispers", "of the Wicked",
			"of the Wind", "of the Wolf", "of the World", "of the Wretched" };
	private static final String[] nm7 = new String[] { "Archangel", "Archdemon", "Arcus", "Armadillo", "Armageddon", "Arrowsong",
			"Arrowspike", "Avalance", "Back Pain", "Backsnipe", "Ballista", "Barrage", "Beesting", "Betrayal", "Betrayal's Sting", "Bolt",
			"Bon Voyage", "Bristleblitz", "Bullseye", "Calamity", "Chimera", "Clutch", "Comet", "Courier", "Crier", "Curvey", "Cyclone",
			"Dash", "Dead Air", "Death's Kiss", "Death's Sigh", "Death's Whisper", "Decimate", "Deliverance", "Deluge", "Destiny's Song",
			"Devil's Kiss", "Devil's Recurve", "Devil's Sting", "Devil's Whisper", "Doomcaster", "Drawback", "Drawling", "Drawstring",
			"Dream Catcher", "Eagle", "Eagle Strike", "Emily's Curve", "Euthanasia", "Falling Star", "Featherdraw", "Firestarter", "Fling",
			"Flux", "Fury", "Gargoyle", "Graviton", "Hailstorm", "Hamstring", "Hamstrung", "Harbinger", "Hatred's Sting", "Hawkeye",
			"Heartbeat", "Heartpiercer", "Heartstriker", "Heartstring", "Hedgehog", "Hell's Whistle", "High-Strung", "Hope's End", "Hornet",
			"Huntress", "Hurricane", "Hush", "Irk", "Jugular", "Kiss of Death", "Lash", "Lightning", "Long Shot", "Messenger", "Meteor",
			"Midge", "Mirage", "Misery's End", "Molten Fury", "Mosquito", "Netherstrand", "Penetrator", "Perfidy", "Phantom",
			"Phantom Strike", "Phoenix", "Pierce", "Pinch", "Pique", "Pluck", "Porcupine", "Precise", "Precision", "Prickle", "Prophet",
			"Puncture", "Quickstrike", "Quintain", "Rain Maker", "Razorsong", "Razorwind", "Recurve", "Rigormortis", "Salvation", "Scorpio",
			"Scorpion", "Shadow Strike", "Shooting Star", "Shriek", "Silent Messenger", "Silentsong", "Siren's Call", "Siren's Cry",
			"Siren's Song", "Sky Piercer", "Skyfire", "Snatch", "Snipe", "Snitch", "Soulstring", "Spitfire", "Splinter", "Splintermark",
			"Squawk", "Squirm", "Star's Fury", "Starshot", "Starstruck", "Sting", "Stormsong", "Stryker", "Surprise", "Swiftwind", "Swoosh",
			"Talonstrike", "Tempest", "The Ambassador", "The Messenger", "Thunder", "Thunderstrike", "Tiebreaker", "Tranquility",
			"Truestrike", "Tweak", "Twisted", "Twister", "Twitch", "Typhoon", "Valkyrie", "Venomstrike", "Viper", "Vixen", "Vulture",
			"Warsong", "Wasp", "Whelm", "Whisper", "Whisperwind", "Windbreaker", "Windforce", "Windlass", "Windrunner", "Windstalker",
			"Windtalker", "WithDraw" };

	private BowNameGenerator() {}

	public static String generateName() {
		String name;
		int roll = RANDOM.nextInt(4);
		int rnd;
		int rnd2;
		int rnd3;
		switch (roll) {
		case 0:
			rnd = (int) Math.floor(Math.random() * nm1.length);
			name = nm1[rnd];
			break;
		case 1:
			rnd = (int) Math.floor(Math.random() * nm2.length);
			rnd2 = (int) Math.floor(Math.random() * nm4.length);
			name = nm2[rnd] + " " + nm4[rnd2];
			break;
		case 2:
			rnd = (int) Math.floor(Math.random() * nm2.length);
			rnd2 = (int) Math.floor(Math.random() * nm3.length);
			rnd3 = (int) Math.floor(Math.random() * nm4.length);
			name = nm2[rnd] + " " + nm3[rnd2] + " " + nm4[rnd3];
			break;
		case 3:
			rnd = (int) Math.floor(Math.random() * nm7.length);
			rnd2 = (int) Math.floor(Math.random() * nm5.length);
			rnd3 = (int) Math.floor(Math.random() * nm6.length);
			name = nm7[rnd] + ", " + nm5[rnd2] + " " + nm6[rnd3];
			break;
		default:
			name = null;
		}
		return name;
	}

}
