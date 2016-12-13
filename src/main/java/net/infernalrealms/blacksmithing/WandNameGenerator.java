package net.infernalrealms.blacksmithing;

import java.util.Random;

public final class WandNameGenerator {

	private static final Random RANDOM = new Random();
	private static final String[] nm1 = new String[] { "Abracadaver", "Alakaslam", "Alpha", "Amnesia", "Amnesty", "Anarchy", "Apocalypse",
			"Apostle", "Armageddon", "Arrogance", "Ataraxia", "Attrition", "Balance", "Benediction", "Betrayal", "Betrayer", "Blackout",
			"Blazefury", "Blind Justice", "Branch of Corruption", "Branch of Illusions", "Branch of Truth", "Branch of Twilight",
			"Branch of Wisdom", "Brilliance", "Brilliancy", "Broken Promise", "Brutality", "Cataclysm", "Catastrophe", "Celeste",
			"Chancellor", "Chaos", "Clemency", "Cloudscorcher", "Cometfell", "Consecration", "Convergence", "Corrupted Will", "Corruption",
			"Crucifix", "Damnation", "Dawn", "Deathraze", "Decimation", "Dementia", "Desolation", "Despair", "Devotion", "Divinity",
			"Doomcaller", "Dragonwrath", "Draughtbane", "Dreambender", "Dreambinder", "Dreamcaller", "Dreamwatcher", "Due Diligence",
			"Dying Hope", "Dying Light", "Earthshaper", "Echo", "Eclipse", "Edge of Eternity", "Edge of Insanity", "Edge of Sanity",
			"Edge of Time", "Enigma", "Enlightenment", "Epilogue", "Erosion", "Eternal Will", "Extinction", "Eye of Corruption",
			"Eye of the Seeker", "Faded Memory", "Faith", "Faithkeeper", "Fate", "Feral Fury", "Firestorm", "Fireweaver", "Flamewarden",
			"Fluke", "Fortuity", "Frenzy", "Frostwarden", "Fury", "Gaze of Arrogance", "Gaze of Corruption", "Gaze of Truth",
			"Gaze of Vanity", "Ghostwalker", "Glimmer", "Gutrender", "Gutwrencher", "Harmony", "Hell's Scream", "Hubris", "Hysteria",
			"Illumination", "Illusion", "Inertia", "Infamy", "Infinity", "Ingenuity", "Insanity", "Insight", "Insolence", "Invocation",
			"Isolation", "Ivory", "Journey's End", "Judgement", "Justice", "Knightfall", "Labyrinth", "Lament", "Lazarus", "Lifebender",
			"Lifebinder", "Lifemender", "Lightbane", "Limbo", "Lorekeeper", "Maelstrom", "Malevolence", "Malice", "Massacre", "Meditation",
			"Mercy", "Midnight", "Moonlight", "Moonshadow", "Moonshine", "Narcoleptic", "Nemesis", "Nightbane", "Nightfall", "Nightfury",
			"Nightkiss", "Nightstaff", "Nimble", "Nirvana", "Oathbreaker", "Oathkeeper", "Oblivion", "Omega", "Omen", "Pansophy",
			"Peacekeeper", "Penance", "Perdition", "Persuasion", "Phantom", "Phantomstrike", "Phobia", "Pilgrimage", "Prudence",
			"Purgatory", "Pursuit", "Quicksilver", "Rage", "Rapture", "Reaper", "Reckoning", "Redemption", "Reflection", "Remorse",
			"Repentance", "Requiem", "Retribution", "Riddle", "Sacrifice", "Sanguine", "Sapience", "Scar", "Scarlet", "Sealed Truth",
			"Serendipity", "Serenity", "Shadowfall", "Shadowsong", "Silence", "Silverlight", "Sleepwalker", "Solarflare", "Soulkeeper",
			"Spellbinder", "Spire", "Spire of Twilight", "Spiritbinder", "Spiritcaller", "Stalk of Corruption", "Stalk of Illusions",
			"Stalk of Truth", "Stalk of Wisdom", "Starlight", "Starshine", "Stoneshaper", "Storm", "Stormbringer", "Stormcaller",
			"Stormtip", "Sufferance", "Sun Strike", "Sunflare", "Sunshard", "Sunshine", "Supinity", "Suspension", "Swan Song", "The End",
			"The Prophecy", "The Spire", "The Stake", "The Taker", "The Undertaker", "The Unmaker", "The Verdict", "The Void", "Thorn",
			"Thunder", "Thunderstorm", "Torment", "Tranquility", "Treachery", "Trickster", "Trinity", "Twilight", "Twinkle",
			"Twisted Visage", "Twisted Visions", "Twister", "Twitch", "Vainglorious", "Valkyrie", "Vanity", "Verdict", "Visage",
			"Visage of Arrogance", "Visage of Truth", "Will of Truth", "Will of the Master", "Windcaller", "Windfall", "Windwalker",
			"Windweaver", "Worldshaper" };
	private static final String[] nm2 = new String[] { "Ancient", "Antique", "Apocalypse", "Apocalyptic", "Arcane", "Arched", "Atuned",
			"Baneful", "Banished", "Barbarian", "Barbaric", "Battleworn", "Blazefury", "Blood Infused", "Blood-Forged", "Bloodcursed",
			"Bloodied", "Bloodlord's", "Bloodsurge", "Bloodvenom", "Bonecarvin", "Burnished", "Cataclysm", "Cataclysmic", "Cold-Forged",
			"Corroded", "Corrupted", "Crazed", "Crying", "Cursed", "Curved", "Dancing", "Dark", "Darkness", "Defiled", "Defiling",
			"Deluded", "Demonic", "Deserted", "Desire's", "Desolation", "Destiny's", "Diabolical", "Dire", "Doom", "Doom's", "Dragon's",
			"Dragonbreath", "Ebon", "Eerie", "Enchanted", "Engraved", "Enlightened", "Eternal", "Exiled", "Extinction", "Faith's",
			"Faithful", "Fancy", "Fearful", "Feral", "Ferocious", "Fierce", "Fiery", "Fire Infused", "Fireguard", "Firesoul", "Firestorm",
			"Flaming", "Forsaken", "Fortune's", "Foul", "Frenzied", "Frost", "Frozen", "Furious", "Fusion", "Ghastly", "Ghost",
			"Ghost-Forged", "Ghostly", "Gleaming", "Glinting", "Greedy", "Grieving", "Grim", "Guardian's", "Hailstorm", "Harmonized",
			"Hateful", "Haunted", "Heartless", "Heinous", "Hollow", "Holy", "Honed", "Hope's", "Hopeless", "Howling", "Hungering",
			"Improved", "Impure", "Incarnated", "Infused", "Inherited", "Jade Infused", "Judgement", "Liar's", "Lich", "Lightning",
			"Lonely", "Loyal", "Lustful", "Lusting", "Mage's", "Malevolent", "Malicious", "Malificent", "Malignant", "Massive", "Mended",
			"Misfortune's", "Misty", "Moonlit", "Mourning", "Necromancer's", "Nightmare", "Oathkeeper's", "Ominous", "Peacekeeper's",
			"Phantom", "Polished", "Possessed", "Pride's", "Prideful", "Primitive", "Promised", "Protector's", "Proud", "Pure", "Putrid",
			"Raging", "Refined", "Reforged", "Reincarnated", "Relentless", "Remorseful", "Renewed", "Renovated", "Replica", "Restored",
			"Retribution", "Ritual", "Roaring", "Ruby Infused", "Rune-Forged", "Runed", "Rusty", "Sage's", "Savage", "Sear's", "Shadow",
			"Shamanic", "Sharpened", "Silent", "Singed", "Singing", "Sinister", "Skyfall", "Smooth", "Solitude's", "Sorcerer's", "Sorrow's",
			"Soul", "Soul Infused", "Soul-Forged", "Soulcursed", "Soulless", "Spectral", "Spectral-Forged", "Spiteful", "Storm",
			"Storm-Forged", "Stormfury", "Stormguard", "Terror", "Thirsting", "Thirsty", "Thunder", "Thunder-Forged", "Thunderfury",
			"Thunderguard", "Thundersoul", "Thunderstorm", "Timeworn", "Tormented", "Treachery's", "Twilight", "Twilight's", "Twisted",
			"Tyrannical", "Undead", "Unholy", "Vengeance", "Vengeful", "Venom", "Vicious", "Vile", "Vindication", "Vindictive", "Void",
			"Volcanic", "Vowed", "War", "War-Forged", "Warlock's", "Warlord's", "Warp", "Warped", "Whistling", "Wicked", "Wind's",
			"Wind-Forged", "Windsong", "Wizard's", "Woeful", "Wrathful", "Wretched", "Yearning", "Zealous" };
	private static final String[] nm3 = new String[] { "Ashwood", "Bone", "Briarwood", "Bronzed", "Cedarwood", "Cottonwood", "Devilwood",
			"Driftwood", "Ebon", "Emberwood", "Hardwood", "Iron", "Ironbark", "Maple", "Oak", "Oakwood", "Redwood", "Sagewood",
			"Sandalwood", "Skeletal", "Steel", "Summerwood", "Titanium", "Warpwood", "Willow", "Yew" };
	private static final String[] nm4 = new String[] { "Wand", "Focus", "Rod", "Pole", "Stave", "Greatwand", "Elder Rod" };
	private static final String[] nm5 = new String[] { "Annihilation", "Betrayer", "Bond", "Boon", "Branch", "Breaker", "Bringer", "Call",
			"Cane", "Champion", "Conqueror", "Crusader", "Cry", "Cunning", "Dawn", "Defender", "Defiler", "Deflector", "Destroyer", "Doom",
			"Edge", "Ender", "Energy Staff", "Executioner", "Favor", "Ferocity", "Foe", "Gift", "Glory", "Grand Staff", "Greatstaff",
			"Guardian", "Heirloom", "Hope", "Incarnation", "Last Hope", "Last Stand", "Legacy", "Memory", "Might", "Oath", "Pact", "Pledge",
			"Pole", "Promise", "Protector", "Reach", "Reaper", "Savagery", "Secret", "Slayer", "Soul", "Spire", "Spiritstaff", "Staff",
			"Terror", "Token", "Tribute", "Vengeance", "Voice", "War Staff", "Warden Staff", "Whisper", "Wit" };
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
			"of Time", "of the East", "of the Emperor", "of the Empty Void", "of the End", "of the Enigma", "of the Fallen",
			"of the Falling Sky", "of the Flame", "of the Forest", "of the Forgotten", "of the Forsaken", "of the Gladiator",
			"of the Harvest", "of the Immortal", "of the Incoming Storm", "of the Insane", "of the King", "of the Lasting Night",
			"of the Leviathan", "of the Light", "of the Lion", "of the Lionheart", "of the Lone Victor", "of the Lone Wolf", "of the Lost",
			"of the Moon", "of the Moonwalker", "of the Night Sky", "of the Night", "of the Nightstalker", "of the North", "of the Occult",
			"of the Oracle", "of the Phoenix", "of the Plague", "of the Prince", "of the Protector", "of the Queen", "of the Serpent",
			"of the Setting Sun", "of the Shadows", "of the Sky", "of the South", "of the Stars", "of the Storm", "of the Summoner",
			"of the Sun", "of the Sunwalker", "of the Talon", "of the Undying", "of the Victor", "of the Void", "of the West",
			"of the Whispers", "of the Wicked", "of the Wind", "of the Wolf", "of the World", "of the Wretched" };
	private static final String[] nm7 = new String[] { "Abracadaver", "Alakaslam", "Alpha", "Amnesia", "Amnesty", "Anarchy", "Apocalypse",
			"Apostle", "Armageddon", "Arrogance", "Ataraxia", "Attrition", "Balance", "Benediction", "Betrayal", "Betrayer", "Blackout",
			"Blazefury", "Blind Justice", "Brilliance", "Brilliancy", "Broken Promise", "Brutality", "Cataclysm", "Catastrophe", "Celeste",
			"Chancellor", "Chaos", "Clemency", "Cloudscorcher", "Cometfell", "Consecration", "Convergence", "Corrupted Will", "Corruption",
			"Crucifix", "Damnation", "Dawn", "Deathraze", "Decimation", "Dementia", "Desolation", "Despair", "Devotion", "Divinity",
			"Doomcaller", "Dragonwrath", "Draughtbane", "Dreambender", "Dreambinder", "Dreamcaller", "Dreamwatcher", "Due Diligence",
			"Dying Hope", "Dying Light", "Earthshaper", "Echo", "Eclipse", "Edge of Eternity", "Edge of Insanity", "Edge of Sanity",
			"Edge of Time", "Enigma", "Enlightenment", "Epilogue", "Erosion", "Eternal Will", "Extinction", "Eye of Corruption",
			"Eye of the Seeker", "Faded Memory", "Faith", "Faithkeeper", "Fate", "Feral Fury", "Firestorm", "Fireweaver", "Flamewarden",
			"Fluke", "Fortuity", "Frenzy", "Frostwarden", "Fury", "Gaze of Arrogance", "Gaze of Corruption", "Gaze of Truth",
			"Gaze of Vanity", "Ghostwalker", "Glimmer", "Gutrender", "Gutwrencher", "Harmony", "Hell's Scream", "Hubris", "Hysteria",
			"Illumination", "Illusion", "Inertia", "Infamy", "Infinity", "Ingenuity", "Insanity", "Insight", "Insolence", "Invocation",
			"Isolation", "Ivory", "Journey's End", "Judgement", "Justice", "Knightfall", "Labyrinth", "Lament", "Lazarus", "Lifebender",
			"Lifebinder", "Lifemender", "Lightbane", "Limbo", "Lorekeeper", "Maelstrom", "Malevolence", "Malice", "Massacre", "Meditation",
			"Mercy", "Midnight", "Moonlight", "Moonshadow", "Moonshine", "Narcoleptic", "Nemesis", "Nightbane", "Nightfall", "Nightfury",
			"Nightkiss", "Nightstaff", "Nimble", "Nirvana", "Oathbreaker", "Oathkeeper", "Oblivion", "Omega", "Omen", "Pansophy",
			"Peacekeeper", "Penance", "Perdition", "Persuasion", "Phantom", "Phantomstrike", "Phobia", "Pilgrimage", "Prudence",
			"Purgatory", "Pursuit", "Quicksilver", "Rage", "Rapture", "Reaper", "Reckoning", "Redemption", "Reflection", "Remorse",
			"Repentance", "Requiem", "Retribution", "Riddle", "Sacrifice", "Sanguine", "Sapience", "Scar", "Scarlet", "Sealed Truth",
			"Serendipity", "Serenity", "Shadowfall", "Shadowsong", "Silence", "Silverlight", "Sleepwalker", "Solarflare", "Soulkeeper",
			"Spellbinder", "Spire", "Spire of Twilight", "Spiritbinder", "Spiritcaller", "Stalk of Corruption", "Stalk of Illusions",
			"Stalk of Truth", "Stalk of Wisdom", "Starlight", "Starshine", "Stoneshaper", "Storm", "Stormbringer", "Stormcaller",
			"Stormtip", "Sufferance", "Sun Strike", "Sunflare", "Sunshard", "Sunshine", "Supinity", "Suspension", "Swan Song", "The End",
			"The Prophecy", "The Stake", "The Taker", "The Undertaker", "The Unmaker", "The Verdict", "The Void", "Thorn", "Thunder",
			"Thunderstorm", "Torment", "Tranquility", "Treachery", "Trickster", "Trinity", "Twilight", "Twinkle", "Twisted Visage",
			"Twisted Visions", "Twister", "Twitch", "Vainglorious", "Valkyrie", "Vanity", "Verdict", "Visage", "Visage of Arrogance",
			"Visage of Truth", "Will of Truth", "Will of the Master", "Windcaller", "Windfall", "Windwalker", "Windweaver", "Worldshaper" };

	private WandNameGenerator() {}

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
