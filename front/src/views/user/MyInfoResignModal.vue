<script setup>
import { defineEmits } from 'vue';

const emit = defineEmits(['resignExecute']);

const resign = () => {
    emit('resignExecute');
}
</script>

<template>
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">회원 탈퇴</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>회원 탈퇴 진행시 복구가 불가능합니다.</p>
                    <p>그럼에도 탈퇴를 진행하시겠습니까?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">아니요</button>
                    <button type="button" class="btn btn-danger" @click="resign">탈퇴하기</button>
                </div>
            </div>
        </div>
    </div>
</template>
<style scoped>
.modal-backdrop {
    opacity: 0.5 !important;
    /* 투명도를 설정하여 색상을 확인 */
    background-color: rgba(0, 0, 0, 0.5) !important;
    /* 반투명 검정색 설정 */
}
</style>