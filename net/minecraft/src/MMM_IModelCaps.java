package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ���f�����ʉ��p�C���^�[�t�F�[�X�B
 * �`���w��̒l�ǂݏo����ModelCapsHelper���g�����ƁB
 * TODO:���o�[�W�����ŐF�X�������ƁB
 */
public interface MMM_IModelCaps {

	/*
	 * �@�\���̌Q�A��芸���������Ă��邯�Ǖʂɔ��Ȃ���Ή��ł��ǂ��B
	 * �ꉞ�\��l���Ă��ƂŁB
	 * �Ǝ��ǉ��̏ꍇ��0x00010000�ȏ���g���ĉ������B
	 */
	// ModelBase
	public static final int caps_onGround			= 0x0001;
	public static final int caps_isRiding			= 0x0002;
	public static final int caps_isChild			= 0x0003;
	/** ���A���^�C����Entity�̃T�C�Y���X�V���� **/
	public static final int caps_isUpdateSize		= 0x0004;
	// ModelBiped
	public static final int caps_heldItemLeft		= 0x0010;
	public static final int caps_heldItemRight		= 0x0011;
	public static final int caps_heldItems			= 0x0012;
	public static final int caps_isSneak			= 0x0013;
	public static final int caps_aimedBow			= 0x0014;
	// EntityCaps
	public static final int caps_Entity					= 0x0020;
	public static final int caps_health					= 0x0021;
	public static final int caps_ticksExisted			= 0x0022;
	public static final int caps_currentEquippedItem	= 0x0023;
	public static final int caps_currentArmor			= 0x0024;
	public static final int caps_healthFloat			= 0x0025;
	
	public static final int caps_isWet					= 0x0030;
	public static final int caps_isDead					= 0x0031;
	public static final int caps_isJumping				= 0x0032;
	public static final int caps_isInWeb				= 0x0033;
	public static final int caps_isSwingInProgress		= 0x0034;
//	public static final int caps_isBlocking				= 0x0035;
	public static final int caps_isBurning				= 0x0036;
	public static final int caps_isInWater				= 0x0037;
	public static final int caps_isInvisible			= 0x0038;
	public static final int caps_isSprinting			= 0x0039;
	/** ��Ɍq����Ă܂�; return boolean; **/
	public static final int caps_isLeeding				= 0x003a;
	/** ������Ă���Entity�̖��O��Ԃ�; return String; **/
	public static final int caps_getRidingName			= 0x003b;
	
	public static final int caps_posX					= 0x0060;
	public static final int caps_posY					= 0x0061;
	public static final int caps_posZ					= 0x0062;
	public static final int caps_pos					= 0x0063;
	public static final int caps_motionX				= 0x0064;
	public static final int caps_motionY				= 0x0065;
	public static final int caps_motionZ				= 0x0066;
	public static final int caps_motion					= 0x0067;
	public static final int caps_boundingBox			= 0x0068;
	public static final int caps_rotationYaw			= 0x0069;
	public static final int caps_rotationPitch			= 0x006a;
	public static final int caps_prevRotationYaw		= 0x006b;
	public static final int caps_prevRotationPitch		= 0x006c;
	public static final int caps_renderYawOffset		= 0x006d;
	
	/** Entity�̈ʒu�ɃI�t�Z�b�g�������W��Block���擾���� [0]:offsetX, [1]:offsetY, [2]:offsetZ **/
//	public static final int caps_PosBlock				= 0x0080;
	/** Entity�̈ʒu�ɃI�t�Z�b�g�������W��BlockID���擾���� [0]:offsetX, [1]:offsetY, [2]:offsetZ **/
	public static final int caps_PosBlockID				= 0x0081;
	/** Entity�̈ʒu�ɃI�t�Z�b�g�������W��BlockMetaData���擾���� [0]:offsetX, [1]:offsetY, [2]:offsetZ **/
	public static final int caps_PosBlockMeta			= 0x0082;
	/** Entity�̈ʒu�ɃI�t�Z�b�g�������W��Block����C�u���b�N�����擾���� [0]:offsetX, [1]:offsetY, [2]:offsetZ **/
	public static final int caps_PosBlockAir			= 0x0083;
	/** Entity�̈ʒu�ɃI�t�Z�b�g�������W��Block����C�u���b�N�����擾���� [0]:offsetX, [1]:offsetY, [2]:offsetZ **/
	public static final int caps_PosBlockLight			= 0x0084;
	/** Entity�̈ʒu�ɃI�t�Z�b�g�������W��Block����C�u���b�N�����擾���� [0]:offsetX, [1]:offsetY, [2]:offsetZ **/
	public static final int caps_PosBlockPower			= 0x0085;
	/** player�ɏ���Ă��邩�𔻒�**/
	public static final int caps_isRidingPlayer			= 0x0086;

	// WorldData
	public static final int caps_WorldTotalTime			= 0xff00;
	public static final int caps_WorldTime				= 0xff01;
	public static final int caps_MoonPhase				= 0xff02;

	// littleMaid
	public static final int caps_isRendering		= 0x0100;
	public static final int caps_isBloodsuck		= 0x0101;
	public static final int caps_isFreedom			= 0x0102;
	public static final int caps_isTracer			= 0x0103;
	public static final int caps_isPlaying			= 0x0104;
	public static final int caps_isLookSuger		= 0x0105;
	public static final int caps_isBlocking			= 0x0106;
	public static final int caps_isWait				= 0x0107;
	public static final int caps_isWaitEX			= 0x0108;
	/** �C���x���g�����J���Ă��邩��Ԃ��܂�(boolean) **/
	public static final int caps_isOpenInv			= 0x0109;
	public static final int caps_isWorking			= 0x010a;
	public static final int caps_isWorkingDelay		= 0x010b;
	public static final int caps_isContract			= 0x010c;
	public static final int caps_isContractEX		= 0x010d;
	public static final int caps_isRemainsC			= 0x010e;
	public static final int caps_isClock			= 0x010f;
	public static final int caps_isMasked			= 0x0110;
	public static final int caps_isCamouflage		= 0x0111;
	public static final int caps_isPlanter			= 0x0112;
	public static final int caps_isOverdrive		= 0x0113;
	public static final int caps_isOverdriveDelay	= 0x0114;
	public static final int caps_entityIdFactor		= 0x0120;
	public static final int caps_height				= 0x0121;
	public static final int caps_width				= 0x0122;
	public static final int caps_YOffset			= 0x0123;
	public static final int caps_mountedYOffset		= 0x0124;
	public static final int caps_dominantArm		= 0x0125;
	public static final int caps_render				= 0x0130;
	public static final int caps_Arms				= 0x0131;
	public static final int caps_HeadMount			= 0x0132;
	/** ���f���ɐݒ肳��Ă���n�[�h�|�C���g��z��ŕԂ��܂� **/
	public static final int caps_HardPoint			= 0x0133;
	/** �������Ă���X�^�r���C�U�[��Ԃ��܂� **/
	public static final int caps_stabiliser			= 0x0134;
	/** ���ݕێ����Ă���A�C�e���̔z���Ԃ��܂� **/
	public static final int caps_Items				= 0x0135;
	/** ���ݕێ����Ă���A�C�e���̋����̔z���Ԃ��܂� **/
	public static final int caps_Actions			= 0x0136;
	/** ���ݕێ����Ă���A�C�e���̐U��񂵏�Ԃ�z��ŕԂ��܂� **/
	public static final int caps_Grounds			= 0x0137;
	/** Inventory��Ԃ��܂� **/
	public static final int caps_Inventory			= 0x0138;
	public static final int caps_Ground				= 0x0139;
	public static final int caps_interestedAngle	= 0x0150;
	
	// PlayerFormLittleMaid
	public static final int caps_ScaleFactor	= 0x0200;
	public static final int caps_PartsVisible	= 0x0201;
	public static final int caps_Posing			= 0x0202;
	public static final int caps_Actors			= 0x0203;
	public static final int caps_PartsStrings	= 0x0204;

	// MMM_test
	public static final int caps_changeModel	= 0x0300;
	public static final int caps_renderFace		= 0x0310;
	public static final int caps_renderBody		= 0x0311;
	public static final int caps_setFaceTexture	= 0x0312;


	/**
	 * ���f�����������Ă���@�\�����X�g�ɓ���ĕԂ��B
	 * @return
	 */
	public Map<String, Integer> getModelCaps();

	/**
	 * ���݂̐ݒ�l��ǂݎ��B
	 * @param pIndex
	 * @return
	 */
	public Object getCapsValue(int pIndex, Object ... pArg);

	/**
	 * �@�\�ԍ��ɒl��ݒ肷��B
	 * @param pIndex
	 * @param pArg
	 * @return
	 */
	public boolean setCapsValue(int pIndex, Object ... pArg);

}
