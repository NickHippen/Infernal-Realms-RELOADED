package net.infernalrealms.blacksmithing;

import java.util.Random;

public final class SwordNameGenerator {

	private static final Random RANDOM = new Random();
	private static final String[] nm1 = new String[] { "Shadowfang", "Azurewrath", "Assurance", "ForgetMeNot", "Red Obsidian",
			"Abyssal Shard", "Aetherius", "Agatha", "Alpha", "Amnesia", "Anduril", "Anger�s Tear", "Apocalypse", "Armageddon", "Arondite",
			"Ashrune", "Betrayal", "Betrayer", "Blackest Heart", "Blackout", "Blade of a Thousand Cuts", "Blade of the Grave", "Blazefury",
			"Blazeguard", "Blight's Plight", "Blind Justice", "Blinkstrike", "Bloodquench", "Bloodweep", "Broken Promise", "Brutality",
			"Cataclysm", "Catastrophe", "Celeste", "Chaos", "Cometfell", "Convergence", "Corruption", "Darkheart", "Darkness", "Dawn",
			"Dawn of Ruins", "Dawnbreaker", "Deathbringer", "Deathraze", "Decimation", "Desolation", "Despair", "Destiny's Song", "Devine",
			"Devourer", "Dirge", "Divine Light", "Doomblade", "Doombringer", "Draughtbane", "Due Diligence", "Early Retirement", "Echo",
			"Piece Maker", "Eclipse", "Endbringer", "Epilogue", "Espada", "Eternal Harmony", "Eternal Rest", "Extinction", "Faithkeeper",
			"Fallen Champion", "Fate", "Final Achievement", "Fleshrender", "Florance", "Frenzy", "Fury", "Ghost Reaver", "Ghostwalker",
			"Gladius", "Glimmer", "Godslayer", "Grasscutter", "Grieving Blade", "Gutrender", "Hatred's Bite", "Heartseeker", "Heartstriker",
			"Hell's Scream", "Hellfire", "Hellreaver", "Hollow Silence", "Honor's Call", "Hope's End", "Infamy", "Interrogator", "Justice",
			"Justifier", "King's Defender", "King's Legacy", "Kinslayer", "Klinge", "Knight's Fall", "Knightfall", "Lament", "Last Rites",
			"Last Words", "Lazarus", "Life's Limit", "Lifedrinker", "Light's Bane", "Lightbane", "Lightbringer", "Lightning", "Limbo",
			"Loyalty", "Malice", "Mangler", "Massacre", "Mercy", "Misery's End", "Morbid Doom", "Morbid Will", "Mournblade", "Narcoleptic",
			"Needle", "Nethersbane", "Night's Edge", "Night's Fall", "Nightbane", "Nightcrackle", "Nightfall", "Nirvana", "Oathbreaker",
			"Oathkeeper", "Oblivion", "Omega", "Orenmir", "Peacekeeper", "Perfect Storm", "Persuasion", "Prick", "Purifier", "Rage",
			"Ragespike", "Ragnarok", "Reaper", "Reaper's Toll", "Reckoning", "Reign of Misery", "Remorse", "Requiem", "Requiem of the Lost",
			"Retirement", "Righteous Might", "Rigormortis", "Savagery", "Scalpel", "Scar", "Seethe", "Severance", "Shadow Strike",
			"Shadowsteel", "Silence", "Silencer", "Silver Saber", "Silverlight", "Skullcrusher", "Slice of Life", "Soul Reaper",
			"Soulblade", "Soulrapier", "Spada", "Spike", "Spineripper", "Spiteblade", "Stalker", "Starshatterer", "Sting", "Stinger",
			"Storm", "Storm Breaker", "Stormbringer", "Stormcaller", "Storm-Weaver", "Striker", "Sun Strike", "Suspension", "Swan Song",
			"The Ambassador", "The Black Blade", "The End", "The Facelifter", "The Light", "The Oculus", "The Stake", "The Untamed",
			"The Unyielding", "The Void", "Thorn", "Thunder", "Toothpick", "Tranquility", "Treachery", "Trinity", "Tyrhung",
			"Unending Tyranny", "Unholy Might", "Valkyrie", "Vanquisher", "Vengeance", "Venom", "Venomshank", "Warmonger", "Widow Maker",
			"Willbreaker", "Winterthorn", "Wit's End", "Witherbrand", "Wolf", "Worldbreaker", "Worldslayer" };
	private static final String[] nm2 = new String[] { "Massive", "Military", "Amber Infused", "Ancient", "Anguish", "Annihilation",
			"Antique", "Arcane", "Arched", "Assassination", "Atuned", "Oathkeeper's", "Bandit's", "Baneful", "Banished", "Barbarian",
			"Barbaric", "Battleworn", "Blazefury", "Blood Infused", "Blood-Forged", "Bloodcursed", "Bloodied", "Bloodlord's", "Bloodsurge",
			"Bloodvenom", "Bone Crushing", "Bonecarvin", "Brutal", "Brutality", "Burnished", "Captain's", "Cataclysm", "Cataclysmic",
			"Cold-Forged", "Corroded", "Corrupted", "Crazed", "Crying", "Cursed", "Curved", "Dancing", "Decapitating", "Defiled", "Demonic",
			"Deserted", "Desire's", "Desolation", "Destiny's", "Dire", "Doom", "Doom's", "Dragon's", "Dragonbreath", "Ebon", "Eerie",
			"Enchanted", "Engraved", "Eternal", "Executing", "Exiled", "Extinction", "Faith's", "Faithful", "Fancy", "Fearful", "Feral",
			"Fierce", "Fiery", "Fire Infused", "Fireguard", "Firesoul", "Firestorm", "Flaming", "Flimsy", "Forsaken", "Fortune's",
			"Fragile", "Frail", "Frenzied", "Frost", "Frozen", "Furious", "Fusion", "Ghastly", "Ghost-Forged", "Ghostly", "Gladiator",
			"Gladiator's", "Gleaming", "Glinting", "Greedy", "Grieving", "Guard's", "Guardian's", "Hailstorm", "Hateful", "Haunted",
			"Heartless", "Hollow", "Holy", "Honed", "Honor's", "Hope's", "Hopeless", "Howling", "Hungering", "Improved", "Incarnated",
			"Infused", "Inherited", "Isolated", "Jade Infused", "Judgement", "Knightly", "Legionnaire's", "Liar's", "Lich", "Lightning",
			"Lonely", "Loyal", "Lustful", "Lusting", "Mage's", "Malevolent", "Malicious", "Malignant", "Mended", "Mercenary",
			"Misfortune's", "Misty", "Moonlit", "Mourning", "Nightmare", "Ominous", "Peacekeeper", "Phantom", "Polished", "Possessed",
			"Pride's", "Prideful", "Primitive", "Promised", "Protector's", "Deluded", "Proud", "Recruit's", "Reforged", "Reincarnated",
			"Relentless", "Remorseful", "Renewed", "Renovated", "Replica", "Restored", "Retribution", "Ritual", "Roaring", "Ruby Infused",
			"Rune-Forged", "Rusty", "Sailor's", "Sapphire Infused", "Savage", "Shadow", "Sharpened", "Silent", "Singed", "Singing",
			"Sinister", "Skullforge", "Skyfall", "Smooth", "Solitude's", "Sorrow's", "Soul", "Soul Infused", "Soul-Forged", "Soulcursed",
			"Soulless", "Spectral", "Spectral-Forged", "Spiteful", "Storm", "Storm-Forged", "Stormfury", "Stormguard", "Terror",
			"Thirsting", "Thirsty", "Thunder", "Thunder-Forged", "Thunderfury", "Thunderguard", "Thundersoul", "Thunderstorm", "Timeworn",
			"Tormented", "Trainee's", "Treachery's", "Twilight", "Twilight's", "Twisted", "Tyrannical", "Undead", "Unholy", "Vengeance",
			"Vengeful", "Venom", "Vicious", "Vindication", "Vindictive", "Void", "Volcanic", "Vowed", "War-Forged", "Warlord's", "Warp",
			"Warped", "Whistling", "Wicked", "Wind's", "Wind-Forged", "Windsong", "Woeful", "Wrathful", "Wretched", "Yearning", "Zealous" };
	private static final String[] nm3 = new String[] { "Adamantite", "Bronze", "Copper", "Diamond", "Glass", "Gold", "Iron", "Mithril",
			"Obsidian", "Silver", "Skeletal", "Steel" };
	private static final String[] nm4 = new String[] { "Blade", "Broadsword", "Claymore", "Defender", "Deflector", "Doomblade",
			"Greatsword", "Guardian", "Katana", "Longsword", "Mageblade", "Protector", "Quickblade", "Rapier", "Reaver", "Sabre",
			"Scimitar", "Shortsword", "Slicer", "Spellblade", "Swiftblade", "Sword", "Warblade", "Skewer", "Carver", "Etcher", "Sculptor",
			"Razor", "Crusader" };
	private static final String[] nm5 = new String[] { "Annihilation", "Betrayer", "Blade", "Blessed Blade", "Blood Blade", "Bond", "Boon",
			"Breaker", "Bringer", "Broadsword", "Butcher", "Call", "Carver", "Champion", "Claymore", "Conqueror", "Crusader", "Cry",
			"Cunning", "Dark Blade", "Dawn", "Defender", "Defiler", "Deflector", "Destroyer", "Doomblade", "Edge", "Ender", "Etcher",
			"Executioner", "Favor", "Ferocity", "Foe", "Gift", "Glory", "Greatsword", "Guardian", "Heirloom", "Hope", "Incarnation", "Jaws",
			"Katana", "Last Hope", "Last Stand", "Legacy", "Longblade", "Longsword", "Mageblade", "Memory", "Might", "Oath", "Pact",
			"Pledge", "Promise", "Protector", "Quickblade", "Rapier", "Ravager", "Razor", "Reach", "Reaper", "Reaver", "Runed Blade",
			"Saber", "Sabre", "Savagery", "Scimitar", "Sculptor", "Secret", "Shortsword", "Skewer", "Slayer", "Slicer", "Soul",
			"Spellblade", "Spine", "Swiftblade", "Sword", "Terror", "Token", "Tribute", "Vengeance", "Voice", "Warblade", "Warglaive",
			"Whisper", "Wit" };
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
	private static final String[] nm7 = new String[] { "Aetherius", "Agatha", "Alpha", "Amnesia", "Anduril", "Apocalypse", "Armageddon",
			"Arondite", "Ashrune", "Betrayal", "Betrayer", "Blackout", "Blazefury", "Blazeguard", "Blinkstrike", "Bloodquench", "Bloodweep",
			"Brutality", "Celeste", "Chaos", "Cometfell", "Convergence", "Darkheart", "Dawn", "Dawnbreaker", "Deathbringer", "Deathraze",
			"Decimation", "Desolation", "Destiny's Song", "Dirge", "Doomblade", "Doombringer", "Draughtbane", "Due Diligence", "Echo",
			"Eclipse", "Endbringer", "Epilogue", "Espada", "Extinction", "Faithkeeper", "Fate", "Fleshrender", "Florance", "Frenzy", "Fury",
			"Ghost Reaver", "Ghostwalker", "Gladius", "Glimmer", "Godslayer", "Grasscutter", "Gutrender", "Hatred's Bite", "Heartseeker",
			"Heartstriker", "Hell's Scream", "Hellfire", "Piece Maker", "Hellreaver", "Honor's Call", "Hope's End", "Infamy",
			"Interrogator", "Justifier", "Kinslayer", "Klinge", "Knightfall", "Lament", "Lazarus", "Lifedrinker", "Light's Bane",
			"Lightbane", "Lightbringer", "Lightning", "Limbo", "Loyalty", "Malice", "Mangler", "Massacre", "Mercy", "Misery", "Mournblade",
			"Narcoleptic", "Needle", "Nethersbane", "Night's Edge", "Night's Fall", "Nightbane", "Nightcrackle", "Nightfall", "Nirvana",
			"Oathbreaker", "Oathkeeper", "Oblivion", "Omega", "Orenmir", "Peacekeeper", "Persuasion", "Prick", "Purifier", "Rage",
			"Ragespike", "Ragnarok", "Reckoning", "Reign", "Remorse", "Requiem", "Retirement", "Rigormortis", "Savagery", "Scalpel", "Scar",
			"Seethe", "Severance", "Shadow Strike", "Shadowsteel", "Silence", "Silencer", "Silver Saber", "Silverlight", "Skullcrusher",
			"Slice of Life", "Soul Reaper", "Soulblade", "Soulrapier", "Spada", "Spike", "Spineripper", "Spiteblade", "Stalker",
			"Starshatterer", "Sting", "Stinger", "Storm", "Storm Breaker", "Stormbringer", "Stormcaller", "Story-Weaver", "Striker",
			"Sun Strike", "Suspension", "Swan Song", "The Ambassador", "The Black Blade", "The End", "The Facelifter", "The Light",
			"The Oculus", "The Stake", "The Untamed", "The Unyielding", "The Void", "Thorn", "Thunder", "Toothpick", "Tranquility",
			"Treachery", "Trinity", "Tyrhung", "Unending Tyranny", "Unholy Might", "Valkyrie", "Vanquisher", "Vengeance", "Venom",
			"Venomshank", "Warmonger", "Widow Maker", "Willbreaker", "Winterthorn", "Wit's End", "Witherbrand", "Wolf", "Worldbreaker",
			"Worldslayer" };

	private SwordNameGenerator() {}

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
