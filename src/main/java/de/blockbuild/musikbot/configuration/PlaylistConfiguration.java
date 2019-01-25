package de.blockbuild.musikbot.configuration;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.blockbuild.musikbot.Bot;

import net.dv8tion.jda.core.entities.User;

public class PlaylistConfiguration extends ConfigurationManager {
	private String playlistName, userName;
	private List<String> playlist;

	public PlaylistConfiguration(Bot bot, User user, String name) {
		super(new File(bot.getMain().getDataFolder(), "/Playlists/" + user.getId() + "/" + name + ".yml"));
		this.userName = user.getName();
		this.playlistName = name;

		readConfig();
	}

	@Override
	public boolean writeConfig() {
		YamlConfiguration config = new YamlConfiguration();

		config.set("User_Name", this.userName);
		config.set("Playlist_Name", this.playlistName);
		config.set("Amount", playlist.size());
		config.set("Playlist", this.playlist);

		return this.saveConfig(config);
	}

	@Override
	public boolean readConfig() {
		YamlConfiguration config = this.loadConfig();

		this.playlist = config.getStringList("Playlist");

		return true;
	}

	public void setPlaylist(AudioPlaylist playlist) {
		for (AudioTrack track : playlist.getTracks()) {
			this.playlist.add(track.getInfo().uri);
		}
	}

	public void setPlaylist(List<String> URL) {
		this.playlist = URL;
	}

	public List<String> getPlaylist() {
		return this.playlist;
	}

	public void addTracks(List<AudioTrack> tracks) {
		for (AudioTrack track : tracks) {
			this.playlist.add(track.getInfo().uri);
		}
	}

	public void addTrack(String URL) {
		this.playlist.add(URL);
	}

	public void addTrack(AudioTrack track) {
		this.playlist.add(track.getInfo().uri);
	}

	public void removeTrack(String URL) {
		this.playlist.remove(URL);
	}

	public void removeTrack(AudioTrack track) {
		this.playlist.remove(track.getInfo().uri);
	}

	public void removeTrack(int track) {
		this.playlist.remove(track);
	}

	public void clearPlaylist() {
		this.playlist.clear();
	}

	public int getAmount() {
		return this.playlist.size();
	}
}
