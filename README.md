# MMMのMOD用ライブラリ MMMLib


## 概要
拙作のMODで使用されるライブラリです。  
とりあえず一緒に入れておいて下さい。


## 利用条件
- 動画等での使用、改造、転載すきにしてもよいのよ？
- ただし、商用利用は除く。
- あと、いかなる意味でも作者は責任をとりませぬ。


## 使い方
- 要Modloader。
- %appdata%/.mincraft/mods/にZIPのまま放りこんで下さい。
- %appdata%/.mincraft/config/mod_MMM_MMMLib.cfgができるので設定はそちらで。


## 設定
- 「renderHacking」はItemRendererの置き換えを指定します。  
  他MOD使用時にどうしても表示がおかしくなる場合はfalseにしてください。  
  但し、拙作のMODの一部で正しく表示が行われなくなります。
- 「isDebugMessage」はMMMLibが出力するデバッグメッセージの設定をします。
- 「startVehicleEntityID」は内部で使用される非生物系のEntityIDを獲得するときの先頭番号を指定します。


## 注意
- 幾つかの自作MODの前提MODとなっているので、入っていないと起動できなくなる場合があります。


## 変更点
- 20130407.1 1.5.1 Rev3 更新
    - MMM_Counterを修正。
- 20130403.1 1.5.1 Rev2 更新
- 20130310.1 1.5.1 Rev1 バージョンアップ
    - アイテムの特殊表示周りを調整。
    - ちょこちょこ修正。
- 20130310.1 1.4.7 Rev6 修正
    - アイテムの特殊表示周りを調整。
- 20130212.1 1.4.7 Rev5 修正
    - decPlayerInventoryをこちらへ移動。
    - MMM_ModelDuoのテクスチャ判定を固く。
    - 野生色の獲得処理をブラッシュアップ。
- 20130210.1 1.4.7 Rev4 修正
    - Figure用スライダーをこっちに。
    - avatar関係の処理を移植。
    - 刺さった矢関係の処理を修正。
    - postRenderで親の移動回転を反映するように。
    - アーマーテクスチャのNULL時の処理を修正。
    - テクスチャのSVCL間通信の処理を修正。
- 20130129.1 1.4.7 Rev3 修正
    - モデルがブランクでも表示するように修正。
- 20130128.1 1.4.7 Rev2 修正
- 20130117.1 1.4.7 Rev1 バージョンアップ
- 20130108.1 1.4.6 Rev5 修正
- 20130104.1 1.4.6 Rev4 修正
- 20121228.1 1.4.6 Rev2 修正
- 20121224.1 1.4.6 Rev2 修正
- 20121222.1 1.4.6 Rev1 修正
- 20121217.1 1.4.5 Rev4 修正
- 20121204.1 1.4.5 Rev3 修正
    - Forge環境下で動かなかったのを修正。
- 20121202.1 1.4.5 Rev2 機能追加
- 20121121.1 1.4.5 Rev1 バージョンアップ
- 20121120.1 1.4.4 Rev1 バージョンアップ
- 20121113.1 1.4.2 Rev2 ちょこっと修正
- 20121021.1 1.4.2 Rev1 バージョンアップ
- 20121021.1 1.3.2 Rev3 更新
    - デバッグ用のやつが悪さをしていたのを修正。
- 20121015.1 1.3.2 Rev2 更新
- 20120922.1 1.3.2 Rev1 リリース
