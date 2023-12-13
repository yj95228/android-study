package uk.co.alt236.bluetoothlelib.resolvers;

import android.bluetooth.BluetoothClass;

public class BluetoothClassResolver {

	public static String resolveDeviceClass(int btClass){
		if (btClass == BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER) {
			return "A/V, Camcorder";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO) {
			return "A/V, Car Audio";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE) {
			return "A/V, Handsfree";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES) {
			return "A/V, Headphones";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO) {
			return "A/V, HiFi Audio";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER) {
			return "A/V, Loudspeaker";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE) {
			return "A/V, Microphone";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_PORTABLE_AUDIO) {
			return "A/V, Portable Audio";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_SET_TOP_BOX) {
			return "A/V, Set Top Box";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_UNCATEGORIZED) {
			return "A/V, Uncategorized";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_VCR) {
			return "A/V, VCR";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CAMERA) {
			return "A/V, Video Camera";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CONFERENCING) {
			return "A/V, Video Conferencing";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER) {
			return "A/V, Video Display and Loudspeaker";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_VIDEO_GAMING_TOY) {
			return "A/V, Video Gaming Toy";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_VIDEO_MONITOR) {
			return "A/V, Video Monitor";
		} else if (btClass == BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET) {
			return "A/V, Video Wearable Headset";
		} else if (btClass == BluetoothClass.Device.COMPUTER_DESKTOP) {
			return "Computer, Desktop";
		} else if (btClass == BluetoothClass.Device.COMPUTER_HANDHELD_PC_PDA) {
			return "Computer, Handheld PC/PDA";
		} else if (btClass == BluetoothClass.Device.COMPUTER_LAPTOP) {
			return "Computer, Laptop";
		} else if (btClass == BluetoothClass.Device.COMPUTER_PALM_SIZE_PC_PDA) {
			return "Computer, Palm Size PC/PDA";
		} else if (btClass == BluetoothClass.Device.COMPUTER_SERVER) {
			return "Computer, Server";
		} else if (btClass == BluetoothClass.Device.COMPUTER_UNCATEGORIZED) {
			return "Computer, Uncategorized";
		} else if (btClass == BluetoothClass.Device.COMPUTER_WEARABLE) {
			return "Computer, Wearable";
		} else if (btClass == BluetoothClass.Device.HEALTH_BLOOD_PRESSURE) {
			return "Health, Blood Pressure";
		} else if (btClass == BluetoothClass.Device.HEALTH_DATA_DISPLAY) {
			return "Health, Data Display";
		} else if (btClass == BluetoothClass.Device.HEALTH_GLUCOSE) {
			return "Health, Glucose";
		} else if (btClass == BluetoothClass.Device.HEALTH_PULSE_OXIMETER) {
			return "Health, Pulse Oximeter";
		} else if (btClass == BluetoothClass.Device.HEALTH_PULSE_RATE) {
			return "Health, Pulse Rate";
		} else if (btClass == BluetoothClass.Device.HEALTH_THERMOMETER) {
			return "Health, Thermometer";
		} else if (btClass == BluetoothClass.Device.HEALTH_UNCATEGORIZED) {
			return "Health, Uncategorized";
		} else if (btClass == BluetoothClass.Device.HEALTH_WEIGHING) {
			return "Health, Weighting";
		} else if (btClass == BluetoothClass.Device.PHONE_CELLULAR) {
			return "Phone, Cellular";
		} else if (btClass == BluetoothClass.Device.PHONE_CORDLESS) {
			return "Phone, Cordless";
		} else if (btClass == BluetoothClass.Device.PHONE_ISDN) {
			return "Phone, ISDN";
		} else if (btClass == BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY) {
			return "Phone, Modem or Gateway";
		} else if (btClass == BluetoothClass.Device.PHONE_SMART) {
			return "Phone, Smart";
		} else if (btClass == BluetoothClass.Device.PHONE_UNCATEGORIZED) {
			return "Phone, Uncategorized";
		} else if (btClass == BluetoothClass.Device.TOY_CONTROLLER) {
			return "Toy, Controller";
		} else if (btClass == BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE) {
			return "Toy, Doll/Action Figure";
		} else if (btClass == BluetoothClass.Device.TOY_GAME) {
			return "Toy, Game";
		} else if (btClass == BluetoothClass.Device.TOY_ROBOT) {
			return "Toy, Robot";
		} else if (btClass == BluetoothClass.Device.TOY_UNCATEGORIZED) {
			return "Toy, Uncategorized";
		} else if (btClass == BluetoothClass.Device.TOY_VEHICLE) {
			return "Toy, Vehicle";
		} else if (btClass == BluetoothClass.Device.WEARABLE_GLASSES) {
			return "Wearable, Glasses";
		} else if (btClass == BluetoothClass.Device.WEARABLE_HELMET) {
			return "Wearable, Helmet";
		} else if (btClass == BluetoothClass.Device.WEARABLE_JACKET) {
			return "Wearable, Jacket";
		} else if (btClass == BluetoothClass.Device.WEARABLE_PAGER) {
			return "Wearable, Pager";
		} else if (btClass == BluetoothClass.Device.WEARABLE_UNCATEGORIZED) {
			return "Wearable, Uncategorized";
		} else if (btClass == BluetoothClass.Device.WEARABLE_WRIST_WATCH) {
			return "Wearable, Wrist Watch";
		}
		return "Unknown, Unknown (class=" + btClass + ")";
	}
}
