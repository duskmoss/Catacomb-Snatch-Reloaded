package com.mojang.mojam.sound;

import java.util.Set;
import java.util.TreeSet;

import paulscode.sound.Library;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;

import com.mojang.mojam.network.TurnSynchronizer;

public class SoundPlayer {

	public static final int BACKGROUND_ID = 1000;
	public static final int TITLE_ID = 1001;
	public static final int ENDING_ID = 1002;

	private static final String BACKGROUND_TRACK = "background music";
	private static final String TITLE_TRACK = "title music";
	private static final String ENDING_TRACK = "ending music";

	private static final int MAX_SOURCES_PER_SOUND = 5;

	private final Class<? extends Library> libraryType;
	private SoundSystem soundSystem;
	private boolean oggPlaybackSupport = true;
	private boolean wavPlaybackSupport = true;
	private boolean muted = false;
	private int playingMusic = 0;

	public SoundPlayer() {
		libraryType = LibraryJavaSound.class;

		try {
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
		} catch (SoundSystemException ex) {
			oggPlaybackSupport = false;
		}

		try {
			SoundSystemConfig.setCodec("wav", CodecWav.class);
		} catch (SoundSystemException ex) {
			wavPlaybackSupport = false;
		}

		try {
			soundSystem = new SoundSystem(libraryType);
		} catch (SoundSystemException ex) {
			soundSystem = null;
		}
	}

	private boolean hasOggPlaybackSupport() {
		return oggPlaybackSupport && soundSystem != null;
	}

	private boolean hasWavPlaybackSupport() {
		return wavPlaybackSupport && soundSystem != null;
	}
	
	public boolean isPlaying(int id){
		if (id == BACKGROUND_ID) {
			return isPlaying(BACKGROUND_TRACK);
		}else if (id == TITLE_ID) {
			return isPlaying(TITLE_TRACK);
		}else if (id == ENDING_ID) {
			return isPlaying(BACKGROUND_TRACK);
		}else{
			return false;
		}
	}

	private boolean isPlaying(String sourceName) {
		if (hasOggPlaybackSupport()) {
			return soundSystem.playing(sourceName);
		}
		return false;
	}

	private void startBackgroundMusic() {
		playingMusic = BACKGROUND_ID;
		String backgroundTrack = "/res/sound/Background "
				+ (TurnSynchronizer.synchedRandom.nextInt(4) + 1) + ".ogg";
		if (!isMuted() && hasOggPlaybackSupport()) {
			soundSystem.backgroundMusic(BACKGROUND_TRACK,
					SoundPlayer.class.getResource(backgroundTrack),
					backgroundTrack, false);
		}
	}

	private void stopBackgroundMusic() {
		if (hasOggPlaybackSupport()) {
			soundSystem.stop(BACKGROUND_TRACK);
		}
	}

	private void playTitleMusic() {
		playingMusic = TITLE_ID;
		String titleTrack = "/res/sound/Title.ogg";
		if (!isMuted() && hasOggPlaybackSupport()) {
			soundSystem
					.backgroundMusic(TITLE_TRACK,
							SoundPlayer.class.getResource(titleTrack),
							titleTrack, false);
		}
	}

	private void stopTitleMusic() {
		if (hasOggPlaybackSupport()) {
			soundSystem.stop(TITLE_TRACK);
		}
	}

	private void playEndingMusic() {
		playingMusic = ENDING_ID;
		String endingTrack = "/res/sound/Ending.ogg";
		if (!isMuted() && hasOggPlaybackSupport()) {
			soundSystem.backgroundMusic(ENDING_TRACK,
					SoundPlayer.class.getResource(endingTrack), endingTrack,
					false);
		}
	}

	private void stopEndingMusic() {
		if (hasOggPlaybackSupport()) {
			soundSystem.stop(ENDING_TRACK);
		}
	}

	public int playMusic(int id) {
		stopMusic();
		if (id == BACKGROUND_ID) {
			startBackgroundMusic();
		}
		if (id == TITLE_ID) {
			playTitleMusic();
		}
		if (id == ENDING_ID) {
			playEndingMusic();
		}
		return id;

	}

	public void stopMusic() {
		if (playingMusic == BACKGROUND_ID) {
			stopBackgroundMusic();
		}
		if (playingMusic == TITLE_ID) {
			stopTitleMusic();
		}
		if (playingMusic == ENDING_ID) {
			stopEndingMusic();
		}
		playingMusic = 0;
	}

	private Set<String> loaded = new TreeSet<String>();

	public void setListenerPosition(float x, float y) {
		soundSystem.setListenerPosition(x, y, 50);
	}

	public boolean playSound(String sourceName, float x, float y) {
		return playSound(sourceName, x, y, false);
	}

	public boolean playSound(String sourceName, float x, float y,
			boolean blocking) {
		return playSound(sourceName, x, y, blocking, 0);
	}

	private boolean playSound(String sourceName, float x, float y,
			boolean blocking, int index) {
		if (index < MAX_SOURCES_PER_SOUND && !isMuted()
				&& hasWavPlaybackSupport()) {
			String indexedSourceName = sourceName + index;
			if (!loaded.contains(indexedSourceName)) {
				soundSystem.newSource(false, indexedSourceName,
						SoundPlayer.class.getResource(sourceName), sourceName,
						false, x, y, 0, SoundSystemConfig.ATTENUATION_ROLLOFF,
						SoundSystemConfig.getDefaultRolloff());
				loaded.add(indexedSourceName);
			} else if (isPlaying(indexedSourceName)) {
				if (blocking) {
					return false;
				}

				// Source already playing, create new source for same sound
				// effect.
				return playSound(sourceName, x, y, false, index + 1);
			}
			soundSystem.stop(indexedSourceName);
			soundSystem.setPriority(indexedSourceName, false);
			soundSystem.setPosition(indexedSourceName, x, y, 0);
			soundSystem.setAttenuation(indexedSourceName,
					SoundSystemConfig.ATTENUATION_ROLLOFF);
			soundSystem.setDistOrRoll(indexedSourceName,
					SoundSystemConfig.getDefaultRolloff());
			soundSystem.setPitch(indexedSourceName, 1.0f);
			soundSystem.play(indexedSourceName);
			return true;
		}
		return false;
	}

	public void shutdown() {
		if (soundSystem != null) {
			soundSystem.cleanup();
		}
	}

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}
}